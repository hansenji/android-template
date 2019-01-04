package org.jdc.template.ux.about

import android.app.Application
import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.ext.saveBodyToFile
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.db.main.type.IndividualType
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.model.webservice.colors.ColorService
import org.jdc.template.model.webservice.colors.dto.DtoColors
import org.jdc.template.ui.viewmodel.BaseViewModel
import org.jdc.template.util.CoroutineContextProvider
import org.jdc.template.work.WorkScheduler
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class AboutViewModel
@Inject constructor(
    cc: CoroutineContextProvider,
    private val analytics: Analytics,
    private val application: Application,
    private val individualRepository: IndividualRepository,
    private val colorService: ColorService,
    private val workScheduler: WorkScheduler
) : BaseViewModel(cc) {

    var appVersion = BuildConfig.VERSION_NAME
    var appBuildDateTime = BuildConfig.BUILD_TIME

    fun logAnalytics() {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_ABOUT).build())
    }

    /**
     * Creates sample data WITH using injection
     */
    fun createSampleDataWithInjection() = launch {
        // clear any existing items
        individualRepository.deleteAllIndividuals()

        val individual1 = Individual()
        individual1.householdId = 1
        individual1.firstName = "Jeff"
        individual1.lastName = "Campbell"
        individual1.phone = "801-555-0000"
        individual1.individualType = IndividualType.HEAD
        individual1.birthDate = LocalDate.of(1970, 1, 1)
        individual1.alarmTime = LocalTime.of(7, 0)

        val individual1a = Individual()
        individual1a.householdId = 1
        individual1a.firstName = "Ty"
        individual1a.lastName = "Campbell"
        individual1a.phone = "801-555-0001"
        individual1a.individualType = IndividualType.HEAD
        individual1a.birthDate = LocalDate.of(1970, 1, 1)
        individual1a.alarmTime = LocalTime.of(7, 0)

        val individual2 = Individual()
        individual2.householdId = 2
        individual2.firstName = "John"
        individual2.lastName = "Miller"
        individual2.phone = "303-555-1111"
        individual2.individualType = IndividualType.CHILD
        individual2.birthDate = LocalDate.of(1970, 1, 2)
        individual2.alarmTime = LocalTime.of(6, 0)

        val individual3 = Individual()
        individual3.householdId = 3
        individual3.firstName = "Jordan"
        individual3.lastName = "Hansen"
        individual3.phone = "385-555-9999"
        individual3.individualType = IndividualType.HEAD
        individual3.birthDate = LocalDate.of(1970, 1, 2)
        individual3.alarmTime = LocalTime.of(6, 30)

        individualRepository.saveNewHousehold("Campbell", listOf(individual1, individual1a))
        individualRepository.saveNewHousehold("Miller", listOf(individual2))
        individualRepository.saveNewHousehold("Hansen", listOf(individual3))
    }

    /**
     * Simple web service call
     */
    fun testQueryWebServiceCall() {
        val call = colorService.colors()

        call.enqueue(object : Callback<DtoColors> {
            override fun onResponse(call: Call<DtoColors>, response: Response<DtoColors>) {
                processWebServiceResponse(response)
            }

            override fun onFailure(call: Call<DtoColors>, t: Throwable) {
                Timber.e(t, "Search FAILED")
            }
        })
    }

    /**
     * Simple web service call using the full url (instead of just an endpoint)
     */
    fun testFullUrlQueryWebServiceCall() {
        val call = colorService.colorsByFullUrl(ColorService.FULL_URL)

        call.enqueue(object : Callback<DtoColors> {
            override fun onResponse(call: Call<DtoColors>, response: Response<DtoColors>) {
                processWebServiceResponse(response)
            }

            override fun onFailure(call: Call<DtoColors>, t: Throwable) {
                Timber.e(t, "Search FAILED")
            }
        })
    }

    /**
     * Web service call that saves response to file, then processes the file (best for large JSON payloads)
     */
    fun testSaveQueryWebServiceCall() {
        val call = colorService.colorsToFile()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // delete any existing file
                val outputFile = File(application.externalCacheDir, "ws-out.json")
                if (outputFile.exists()) {
                    outputFile.delete()
                }

                // save the response body to file
                response.saveBodyToFile(outputFile)

                // show the output of the file
                val fileContents = outputFile.readText()
                Timber.i("Output file: [$fileContents]")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Timber.e("Search FAILED")
            }
        })
    }

    private fun processWebServiceResponse(response: Response<DtoColors>) {
        if (response.isSuccessful) {
            Timber.i("Search SUCCESS")
            response.body()?.let {
                processSearchResponse(it)
            }
        } else {
            Timber.e("Search FAILURE: code (%d)", response.code())
        }
    }

    private fun processSearchResponse(dtoColors: DtoColors) {
        for (dtoResult in dtoColors.colors) {
            Timber.i("Result: %s", dtoResult.colorName)
        }
    }

    /**
     * Sample for creating a scheduled simple worker
     */
    fun workManagerSimpleTest() {
        workScheduler.scheduleSimpleWork("test1")
        workScheduler.scheduleSimpleWork("test2")
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        workScheduler.scheduleSimpleWork("test3")
    }

    /**
     * Sample for creating a scheduled sync worker
     */
    fun workManagerSyncTest() {
        workScheduler.scheduleSync()
        workScheduler.scheduleSync(true)
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        workScheduler.scheduleSync()
    }

    /**
     * Table change listener tests
     */
    fun testTableChange() = launch {
        // Sample tests
        if (individualRepository.getIndividualCount() == 0L) {
            Timber.e("No data.. cannot perform test")
            return@launch
        }

        // Make some changes
        val originalName: String

        val individualList = individualRepository.getAllIndividuals()
        if (individualList.isNotEmpty()) {
            val individual = individualList[0]
            originalName = individual.firstName
            Timber.i("ORIGINAL NAME = %s", originalName)

            // change name
            individual.firstName = "Bobby"
            individualRepository.saveIndividual(individual)

            // restore name
            individual.firstName = originalName
            individualRepository.saveIndividual(individual)
        } else {
            Timber.e("Cannot find individual")
        }
    }

    fun testStuff() = launch {
        individualRepository.getAllMembers().forEach {
            Timber.i("Member Info: $it")
        }
    }
}
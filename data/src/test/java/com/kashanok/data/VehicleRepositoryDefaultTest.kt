package com.kashanok.data

import com.kashanok.data.repository.VehicleRepositoryDefault
import com.kashanok.domain.entity.vehicle.CoordinateParam
import org.junit.Test

class VehicleRepositoryDefaultTest {

    @Test
    fun testREalRequest() {
        val testUrl = "http://kiparo.ru/t/fake-api/car-service.php"
        val repository = VehicleRepositoryDefault(testUrl)

//        val mockedServer = MockWebServer()
//        mockedServer.start()
//        val mockedResponse = MockResponse()
//        mockedResponse.setResponseCode(200)
//        mockedResponse.setBody("{\n" +
//                "\t\"poiList\": [{\n" +
//                "\t\t\t\"id\": 794466,\n" +
//                "\t\t\t\"coordinate\": {\n" +
//                "\t\t\t\t\"latitude\": 53.63866991354654,\n" +
//                "\t\t\t\t\"longitude\": 9.990935212464525\n" +
//                "\t\t\t},\n" +
//                "\t\t\t\"fleetType\": \"TAXI\",\n" +
//                "\t\t\t\"heading\": 207.70917414938233\n" +
//                "\t\t}, {\n" +
//                "\t\t\t\"id\": 604462,\n" +
//                "\t\t\t\"coordinate\": {\n" +
//                "\t\t\t\t\"latitude\": 53.64643192135365,\n" +
//                "\t\t\t\t\"longitude\": 9.895144285030804\n" +
//                "\t\t\t},\n" +
//                "\t\t\t\"fleetType\": \"TAXI\",\n" +
//                "\t\t\t\"heading\": 268.91575306545496\n" +
//                "\t\t}\n" +
//                "\t]\n" +
//                "}")

        val coordinateParam = Pair(CoordinateParam(23423.0, 342.0), CoordinateParam(3242.0, 3453.0))

//        val testSubscriber  = TestSingleSubscriber()

        repository.fetch(coordinateParam).test().assertComplete()
    }

}
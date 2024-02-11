package com.shindra.acafsxb.core.network.sources

import com.shindra.acafsxb.core.network.bo.NetworkAirplane
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NinjaDataSource {
    suspend fun planeByEngineType(type : String): List<NetworkAirplane>
}

class NinjaDataSourceNetowrk @Inject constructor(
    private val client: HttpClient
) : NinjaDataSource {

    override suspend fun planeByEngineType(type : String): List<NetworkAirplane> = withContext(Dispatchers.IO) {
        client.get {
            url {
                parameters.append("engine_type", type)
                parameters.append("limit", "30")
            }
        }.body()
    }

}
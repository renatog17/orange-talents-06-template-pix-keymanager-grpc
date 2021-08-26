package br.com.zup.edu.client.bcb

import com.google.rpc.QuotaFailure
import javax.management.monitor.StringMonitor

class Problem(
    val type:String,
    val status:Int,
    val title: String,
    val detail:String
) {
}

class Violation(
    val field:String,
    val messagem:String
){

}
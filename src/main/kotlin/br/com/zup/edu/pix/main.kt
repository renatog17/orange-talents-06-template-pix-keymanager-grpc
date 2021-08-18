package br.com.zup.edu.pix

fun main() {
    val s = "123.56.789-25"
    if(s.matches("[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}".toRegex())){
        println("entrout")
    }
}
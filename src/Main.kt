//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun getPrice(json: String, itemId: String) : Double {

    val itemStart = json.indexOf("\"$itemId\":")
    if (itemStart == -1) return 0.0
    val priceKey = "\"sellPrice\":"
    val priceStart = json.indexOf(priceKey, itemStart) + priceKey.length
    val priceEnd = json.indexOfFirst { it == ',' || it == '}' }.let {
        json.indexOf(',', priceStart)
    }
    return json.substring(priceStart, priceEnd).trim().toDouble()
}




fun main() {


    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.hypixel.net/skyblock/bazaar"))
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    val body = response.body()
    val sulphurPrice = getPrice(body, "ENCHANTED_SULPHUR")
    val coalPrice = getPrice(body, "ENCHANTED_COAL")
    val gabagoolPrice = getPrice(body, "VERY_CRUDE_GABAGOOL")

    println("Gabagool Price: ${gabagoolPrice.toInt()} " +
            "\nCoal Price: ${coalPrice.toInt()}" +
            "\nSulfur Price: ${sulphurPrice.toInt()}")

    var sulphur: Int = 76
    var gabagool: Int = 36
    var coal: Int = 1212

    print("Enter amount: ")
    var amount = readln().toInt()
    sulphur *= amount
    gabagool *= amount
    coal *= amount
    var splitCoal = coal/4
    println("\nGabagool: $gabagool")

    if (splitCoal > 71000) {

        splitCoal /= 2
        println("Coal: $coal (($splitCoal)x8)")


    } else {

        println("Coal: $coal (($splitCoal)x4)")

    }
    println("Sulphur: $sulphur")

}
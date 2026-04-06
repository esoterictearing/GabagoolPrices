import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun getPrice(json: String, itemId: String, orderType: String) : Double {

    val itemStart = json.indexOf("\"$itemId\":")
    if (itemStart == -1) return 0.0
    val priceKey = "\"$orderType\":"
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

    val sulphurPrice = getPrice(body, "ENCHANTED_SULPHUR", "sellPrice").toInt()
    val coalPrice = getPrice(body, "ENCHANTED_COAL", "sellPrice").toInt()
    val gabagoolPrice = getPrice(body, "VERY_CRUDE_GABAGOOL", "sellPrice").toInt()

    var sulphur = 76
    var gabagool = 36
    var coal = 1212

    print("\nEnter amount: ")
    val amount = readln().toInt()

    sulphur *= amount
    gabagool *= amount
    coal *= amount
    val splitCoal = coal/4
    println("\nGabagool: $gabagool")
    val coalCost = coal * coalPrice

    if (splitCoal > 71000) {

        val remainder = coal % 71000
        val orderAmount = coal / 71000

        println("Coal: $orderAmount orders of 71k, 1 order of $remainder")

    } else {

        println("Coal: $coal (($splitCoal)x4)")

    }
    println("Sulphur: $sulphur")

    val gabagoolCost = gabagool * gabagoolPrice
    val sulphurCost = sulphur * sulphurPrice
    val totalCost = coalCost + gabagoolCost + sulphurCost

    print("\nGabagool will cost %,d".format(gabagoolCost))
    print("\nCoal will cost %,d".format(coalCost))
    print("\nSulphur will cost %,d".format(sulphurCost))
    println("\nTotal is %,d".format(totalCost))


    val profitEstimate = getPrice(body, "HYPERGOLIC_GABAGOOL", "buyPrice").toInt() * amount - totalCost
    print("\nEstimated profit: %,d".format(profitEstimate))

}
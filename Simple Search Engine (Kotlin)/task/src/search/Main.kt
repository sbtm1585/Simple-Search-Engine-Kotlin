package search

fun main(args: Array<String>) {
    var b = true
    val search = Search(args)

    while (b) {
        search.menu()
        b = search.getInput()
    }
}
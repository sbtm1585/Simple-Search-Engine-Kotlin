package search

import java.io.File

class Search(args: Array<String>) {
    private val list = mutableListOf<String>()
    private val set = mutableSetOf<String>()

    init {
        if (args.isNotEmpty()) {
            if (args[0] == "--data" && args.size > 1) {
                val dataSource = File(args[1])
                dataSource.forEachLine { list.add(it) }
            }
        }
    }

    fun menu() {
        println("""
            
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent())
    }

    fun getInput(): Boolean {
        when (readln()) {
            "1" -> searchQueries()
            "2" -> printAll()
            "0" -> {
                println("\nBye!")
                return false
            }
            else -> println("\nIncorrect option! Try again.")
        }
        return true
    }

    private fun printAll() {
        println("\n=== List of people ===").run { list.forEach { println(it) } }
    }

    private fun searchQueries() {
        val strategy = println("Select a matching strategy: ALL, ANY, NONE").run { readln().uppercase() }
        val query = println("\nEnter a name or email to search all suitable people.").run { readln().lowercase() }.split(" ")
        when (strategy) {
            "ALL" -> searchAll(query)
            "ANY" -> searchAny(query)
            "NONE" -> searchNone(query)
            else -> TODO()
        }
    }

    private fun searchAny(query: List<String>) {
        for (entry in query) {
            list.filter { entry in it.lowercase() }.forEach { set.add(it) }
        }
        printResults(set)
    }

    private fun searchAll(query: List<String>) {
        list.filter { it.lowercase().split(" ").containsAll(query) }.forEach { set.add(it) }
        printResults(set)
    }

    private fun searchNone(query: List<String>) {
        for (entry in query) {
            list.filter { entry in it.lowercase() }.forEach { set.add(it) }
        }
        printResults(list.subtract(set))
    }

    private fun printResults(set: Set<String>) {
        if (set.isNotEmpty()) {
            println("\n${set.size} person${if (set.size > 1) "s" else ""} found:")
            set.forEach { println(it) }
            this.set.clear()
        } else {
            println("No matching people found.")
        }
    }
}
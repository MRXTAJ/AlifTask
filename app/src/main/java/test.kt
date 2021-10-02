
fun main(){
    val list = listOf(1, 2, 3, 4, 5)
   val data = list.withIndex().groupBy {
        it.index / 3
    }.map {
        it.value.map {
            it.value
        }
    }
    print(data)
}
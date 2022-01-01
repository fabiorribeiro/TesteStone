import kotlin.math.roundToInt

// Para testar, preencher as variáveis abaixo:

val despesas = listOf<Despesa>(
    Despesa("Passeio de escuna", 5f, 8000),
    Despesa("Diária do hotel", 3f, 33799),
    Despesa("Almoço self-service", 0.757f, 4599)
)

val pessoas = listOf<String>(
    "Fábio", "Lucas", "Karen", "Carol"
)

fun main() {
    calcularDespesas(despesas, pessoas)
}

/**
 * Processa a lista de despesas e de pessoas para calcular o total gasto e o quanto cada pessoa deve.
 *
 * Imprime:
 * - O total de despesas
 * - Com quantas pessoas as despesas serão rateadas
 * - Uma lista com o nome de cada pessoa e quanto ela deve
 *
 * @param listaDespesas Lista de despesas a ser rateada
 * @param listaPessoas Lista de pessoas com quem serão rateadas as despesas. Se na lista contiver nomes repetidos eles serão considerados apenas como uma pessoa
 */
fun calcularDespesas(listaDespesas: List<Despesa>, listaPessoas: List<String>) {
    val pessoas = listaPessoas.distinct()

    if (listaDespesas.isEmpty()) {
        println("Não foi informada nenhuma despesas. Parece que todos quiseram economizar.")
        return
    }

    val totalDespesas: Int = somarDespesas(listaDespesas)
    println("Total de despesas: ${intToReal(totalDespesas)}")

    val totalPessoas: Int = pessoas.count()
    println("Total de pessoas: $totalPessoas")
    if (totalPessoas == 0) {
        println("Nenhuma pessoa encontrada. Acho que vou ter que bancar os gastos de todos.")
        return
    }

    println("----------")

    val despesasPorPessoa = despesasPorPessoa(totalDespesas, pessoas)
    despesasPorPessoa.forEach { despesa ->
        if (despesa.value > 0) {
            println("${despesa.key}: ${intToReal(despesa.value)}")
        }
    }
}

/**
 * Calcula a soma de uma lista de despesas
 * @param despesas A lista de despesas a serem somadas
 * @return Retorna a soma de todas as despesas da lista
 */
fun somarDespesas(despesas: List<Despesa>): Int {
    var totalDespesas = 0
    despesas.forEach { despesa ->
        totalDespesas += despesa.totalDespesa()
    }

    return totalDespesas
}

/**
 * Faz o rateio das despesas com uma lista de pessoas
 * @param totalDespesas É o valor total das despesas que será rateado entre as pessoas informadas na lista 'pessoas'
 * @param pessoas Uma lista de nomes de pessoas pelas quais as despesas serão rateadas
 * @return Retorna um Map onde a chave é um nome das pessoas contidas na lista 'pessoas' e o valor é a divisão uniforme das despesas pela quantidade de despesas desta lista.
 */
fun despesasPorPessoa(totalDespesas: Int, pessoas: List<String>): Map<String, Int> {
    val dic = mutableMapOf<String, Int>()
    val totalPessoas = pessoas.count()

    val porPessoa = totalDespesas / totalPessoas
    var diferenca = totalDespesas - (totalPessoas * porPessoa)

    pessoas.forEach {
        dic[it] = porPessoa + if (diferenca-- > 0) 1 else 0
    }

    return dic
}

/**
 * Função que converte um valor inteiro, que representa os centavos de Real e converte para uma string formatada em Real
 * @param valor Valor que representa quantos centavos quer converter
 * @return Retorna uma String formatada em Real, neste formato: 'R$ 0,00'
 */
fun intToReal(valor: Int): String {
    val valorReal = valor / 100f
    return "R$ %,.2f".format(valorReal)
}

/**
 * Classe que representa uma despesa
 * @param nome Nome da despesa
 * @param quantidade Quantidade
 * @param valorUnitario Valor unitário da despesa
 */
class Despesa(val nome: String, val quantidade: Float, val valorUnitario: Int) {

    /**
     * Calcula o valor total desta despesa
     * @return Retorna o total desta despesa em centavos de Real
     */
    fun totalDespesa(): Int {
        return (quantidade * valorUnitario).roundToInt()
    }
}

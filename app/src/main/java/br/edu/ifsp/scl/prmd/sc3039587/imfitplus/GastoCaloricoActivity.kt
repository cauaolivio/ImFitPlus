package br.edu.ifsp.scl.prmd.sc3039587.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.prmd.sc3039587.imfitplus.databinding.ActivityGastoCaloricoBinding
import kotlin.math.roundToInt

class GastoCaloricoActivity : AppCompatActivity() {

    private val agcb: ActivityGastoCaloricoBinding by lazy {
        ActivityGastoCaloricoBinding.inflate(layoutInflater)
    }

    private lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(agcb.root)

        setSupportActionBar(agcb.toolbarIn.barraFerramentas)
        supportActionBar?.subtitle = getString(R.string.gasto_calorico_subtitle)

        dadosPessoais = intent.getParcelableExtra(Constant.EXTRA_PERFIL)
            ?: run {
                Toast.makeText(
                this, R.string.erro_dados_nao_encontrados,Toast.LENGTH_SHORT).show();
                finish()
                return
            }

        calcularEExibirGastoCalorico()

        agcb.calcularPesoIdealBt.setOnClickListener {
            val intent = Intent(this, PesoIdealActivity::class.java).apply {
                putExtra(Constant.EXTRA_PERFIL, dadosPessoais)
            }
            startActivity(intent)
        }

        agcb.voltarBt.setOnClickListener {
            finish()
        }
    }

    private fun calcularEExibirGastoCalorico() {
        val tmb = calcularTmb(dadosPessoais)
        val fatorAtividade = obterFatorAtividade(dadosPessoais.nivelAtividade)
        val gastoCaloricoDiario = tmb * fatorAtividade

        dadosPessoais.tmb = tmb.roundToInt().toDouble()
        dadosPessoais.gastoCaloricoDiario = gastoCaloricoDiario.roundToInt().toDouble()

        agcb.tmbValorTv.text = getString(R.string.valor_kcal_dia, dadosPessoais.tmb.roundToInt())
        agcb.gastoDiarioValorTv.text = getString(R.string.valor_kcal_dia, dadosPessoais.gastoCaloricoDiario.roundToInt())
    }

    private fun calcularTmb(dados: DadosPessoais): Double {
        val peso = dados.peso
        val alturaCm = dados.altura * 100
        val idade = dados.idade

        return if (dados.sexo == "M") {
            66 + (13.7 * peso) + (5 * alturaCm) - (6.8 * idade)
        } else {
            655 + (9.6 * peso) + (1.8 * alturaCm) - (4.7 * idade)
        }
    }

    private fun obterFatorAtividade(nivel: String): Double {
        return when (nivel) {
            "Sedentário" -> 1.2
            "Leve" -> 1.375
            "Moderado" -> 1.55
            "Intenso" -> 1.725
            else -> 1.2
        }
    }
}
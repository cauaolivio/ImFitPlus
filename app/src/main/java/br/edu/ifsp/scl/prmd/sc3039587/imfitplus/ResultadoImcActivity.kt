package br.edu.ifsp.scl.prmd.sc3039587.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.prmd.sc3039587.imfitplus.databinding.ActivityResultadoImcBinding
import java.text.DecimalFormat

class ResultadoImcActivity : AppCompatActivity() {

    private val arib: ActivityResultadoImcBinding by lazy {
        ActivityResultadoImcBinding.inflate(layoutInflater)
    }

    private lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(arib.root)

        setSupportActionBar(arib.toolbarIn.barraFerramentas)
        supportActionBar?.subtitle = getString(R.string.resultado_imc_subtitle)

        dadosPessoais = intent.getParcelableExtra(Constant.EXTRA_PERFIL)
            ?: run {
                Toast.makeText(this, R.string.erro_dados_nao_encontrados, Toast.LENGTH_SHORT).show()
                finish()
                return
            }
        exibirResultado()

        arib.calcularGastoCaloricoBt.setOnClickListener {
            val intent = Intent(this, GastoCaloricoActivity::class.java).apply {
                putExtra(Constant.EXTRA_PERFIL, dadosPessoais)
            }
            startActivity(intent)
        }

        arib.voltarBt.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun exibirResultado() {
        val imc = dadosPessoais.imc
        val imcFormatado = DecimalFormat("#.##").format(imc)

        val categoria = classificarImc(imc)

        arib.nomeResultadoTv.text = getString(R.string.resultado_nome, dadosPessoais.nome)
        arib.imcValorTv.text = imcFormatado
        arib.categoriaValorTv.text = categoria
    }
    private fun classificarImc(imc: Double): String {
        return when {
            imc < 18.5 -> getString(R.string.categoria_abaixo_peso)
            imc < 25.0 -> getString(R.string.categoria_normal)
            imc < 30.0 -> getString(R.string.categoria_sobrepeso)
            else -> getString(R.string.categoria_obesidade)
        }
    }
}
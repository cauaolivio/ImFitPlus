package br.edu.ifsp.scl.prmd.sc3039587.imfitplus

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.prmd.sc3039587.imfitplus.databinding.ActivityPesoIdealBinding
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.abs

class PesoIdealActivity : AppCompatActivity() {

    private val apib: ActivityPesoIdealBinding by lazy {
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    private lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apib.root)

        setSupportActionBar(apib.toolbarIn.barraFerramentas)
        supportActionBar?.subtitle = getString(R.string.peso_ideal_subtitle)

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_PERFIL, DadosPessoais::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Constant.EXTRA_PERFIL)
        } ?: run {
            Toast.makeText(this, R.string.erro_dados_nao_encontrados, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        calcularEExibirPesoIdeal()

        apib.finalizarBt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun calcularEExibirPesoIdeal() {
        val altura = dadosPessoais.altura
        val pesoAtual = dadosPessoais.peso

        val pesoIdeal = 22.0 * altura.pow(2)

        val diferencaKg = pesoAtual - pesoIdeal

        dadosPessoais.pesoIdeal = pesoIdeal

        val df = DecimalFormat("#.##")

        apib.pesoIdealValorTv.text = getString(R.string.valor_peso_kg, df.format(pesoIdeal))

        val diferencaAbs = abs(diferencaKg)
        val mensagemDiferenca: String

        if (diferencaKg > 0) {
            mensagemDiferenca = getString(R.string.diferenca_perder_simples, df.format(diferencaAbs))
        } else if (diferencaKg < 0) {
            mensagemDiferenca = getString(R.string.diferenca_ganhar_simples, df.format(diferencaAbs))
        } else {
            mensagemDiferenca = getString(R.string.diferenca_ideal_simples)
        }

        apib.diferencaValorTv.text = mensagemDiferenca
    }
}
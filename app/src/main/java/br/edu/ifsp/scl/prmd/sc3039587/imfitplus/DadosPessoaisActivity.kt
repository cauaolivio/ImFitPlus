package br.edu.ifsp.scl.prmd.sc3039587.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.prmd.sc3039587.imfitplus.databinding.ActivityDadosPessoaisBinding
import kotlin.math.pow

class DadosPessoaisActivity : AppCompatActivity() {

    private val adpb: ActivityDadosPessoaisBinding by lazy {
        ActivityDadosPessoaisBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(adpb.root)

        setSupportActionBar(adpb.toolbarIn.barraFerramentas)
        supportActionBar?.subtitle = getString(R.string.dados_pessoais_subtitle)

        adpb.calcularImcBt.setOnClickListener {
            val dados = verificarPreenchimentoEEnviar()

            if (dados != null) {
                val intent = Intent(this, ResultadoImcActivity::class.java).apply {
                    putExtra(Constant.EXTRA_PERFIL, dados)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, R.string.erro_campo_vazio, Toast.LENGTH_SHORT).show()
            }
        }

        adpb.voltarBt.setOnClickListener {
            finish()
        }
    }

    private fun verificarPreenchimentoEEnviar(): DadosPessoais? {
        val nome = adpb.nomeEt.text.toString().trim()
        val idadeStr = adpb.idadeEt.text.toString().trim()
        val pesoStr = adpb.pesoEt.text.toString().trim()
        val alturaStr = adpb.alturaEt.text.toString().trim()

        if (nome.isBlank() || idadeStr.isBlank() || pesoStr.isBlank() || alturaStr.isBlank()) {
            return null
        }

        val altura = alturaStr.toDoubleOrNull()
        val peso = pesoStr.toDoubleOrNull()
        val idade = idadeStr.toIntOrNull()

        if (altura == null || altura <= 0.0 || peso == null || peso <= 0.0 || idade == null || idade <= 0) {
            Toast.makeText(this, R.string.erro_dados_numericos, Toast.LENGTH_SHORT).show()
            return null
        }

        val nivelAtividadeSelecionado = adpb.atividadeSpinner.selectedItem.toString()
        val sexo = if (adpb.masculinoRb.isChecked) "M" else "F"
        val atividade = nivelAtividadeSelecionado.split(" (").first()
        val imcCalculado = calcularImc(peso, altura)

        return DadosPessoais(
            nome = nome,
            idade = idade,
            sexo = sexo,
            altura = altura,
            peso = peso,
            nivelAtividade = atividade,
            imc = imcCalculado
        )
    }

    private fun calcularImc(peso: Double, altura: Double): Double {
        return peso / altura.pow(2)
    }
}
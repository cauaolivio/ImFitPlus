package br.edu.ifsp.scl.prmd.sc3039587.imfitplus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import br.edu.ifsp.scl.prmd.sc3039587.imfitplus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.barraFerramentas)
        supportActionBar?.subtitle = getString(R.string.main_subtitle)
        amb.comecarBt.setOnClickListener {
            startActivity(Intent(this, DadosPessoaisActivity::class.java))
        }
    }
}
package br.edu.ifsp.scl.prmd.sc3039587.imfitplus

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DadosPessoais(
    val nome: String,
    val idade: Int,
    val sexo: String,
    val altura: Double,
    val peso: Double,
    val nivelAtividade: String,
    val imc: Double,
    var tmb: Double = 0.0,
    var gastoCaloricoDiario: Double = 0.0,
    var pesoIdeal: Double = 0.0
) : Parcelable
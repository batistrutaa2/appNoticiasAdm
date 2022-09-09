package com.example.appdenoticiasadm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.appdenoticiasadm.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private val db = FirebaseFirestore.getInstance()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar!!.hide()
    binding.btPublicarNoticia.setOnClickListener {
      val titulo = binding.tituloNoticia.text.toString()
      val noticia = binding.noticia.text.toString()
      val data = binding.dataPublicacao.text.toString()
      val autor = binding.autor.text.toString()

      if (titulo.isEmpty() || noticia.isEmpty() || data.isEmpty() || autor.isEmpty()) {
        Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
      } else {
        salvarnoticia(titulo, noticia, data, autor)
      }

    }

  }

  private fun salvarnoticia(titulo: String, noticia: String, data: String, autor: String) {
    val mapNoticias = hashMapOf(
      "titulo" to titulo,
      "noticia" to noticia,
      "data" to data,
      "autor" to autor,
    )

    db.collection("noticias").document("noticia")
      .set(mapNoticias).addOnCompleteListener { tarefa ->
        if (tarefa.isSuccessful) {
          Toast.makeText(this, "Noticia Publicada com sucesso!", Toast.LENGTH_LONG).show()
        } else {
          Toast.makeText(this, "Erro ao publicar noticia!", Toast.LENGTH_LONG).show()
        }
      }
  }
}
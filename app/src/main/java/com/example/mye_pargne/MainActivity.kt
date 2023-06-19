package com.example.mye_pargne

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jwtToken = JWTToken.getInstance(this)
        // je recupère les données de mon token
        val token = (JWTToken.getInstance(this)).token.replace("Bearer ","")
        val id_token = JWTToken.getIdUser(token)

        var id= CompteService(0);
        var idInt = 0;
        if(id_token !== null){
             id = CompteService(id_token.toInt());
            idInt = id_token.toInt()

        }



        // fonction qui affiche le solde
        fun SoldeActu(id : CompteService)  {
            val soldeResponse = AuthentificationService.compteRoutes.getSolde(jwtToken.token, id)
            soldeResponse.enqueue(object : Callback<compteSolde> {
                override fun onResponse(call: Call<compteSolde>, response: Response<compteSolde>) {

                    val body = response.body()
                    Log.d("mon solde !", body.toString())

                    if (response.code() == 200 && body != null) {
                        // La on affiche les voitures
                        val textViewSolde = findViewById<TextView>(R.id.soldeCompte)
                        textViewSolde.setText(body.solde.toString() + " €")

                    } else {
                        // Reset du token et retour vers login
                        //jwtToken.setTokenValue("")

                        Toast.makeText( this@MainActivity,"Error during loading of solde", Toast.LENGTH_SHORT).show()

                        // je le fais revenir sur ma page de connexion
                        //val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        //startActivity(intent)
                    }

                }

                override fun onFailure(call: Call<compteSolde>, t: Throwable) {
                    // Reset du token et retour vers login
                    jwtToken.setTokenValue("")

                    Toast.makeText( this@MainActivity,"Error during loading of soldeeeeeeee", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            })
        }


        // affichage de mon solde
        SoldeActu(id)

        // affichage de mes operations des dernieres 24heures
        val listeOpe = findViewById<RecyclerView>(R.id.rcvOperation)
        listeOpe.layoutManager = LinearLayoutManager(this)

        // fonction quin affiche mon recyclerView
        fun opeActu (id : Int){
            val operationResponse = AuthentificationService.operationRoutes.getOpe(jwtToken.token, id)

            operationResponse.enqueue(object : Callback<Operation> {
                override fun onResponse(call: Call<Operation>, response: Response<Operation>) {

                    val body = response.body()
                    Log.d("mon solde !", body.toString())

                    if (response.code() == 200 && body != null) {
                        // La on affiche mes données dans le recycler view
                        listeOpe.adapter = OperationAdaptateur(this@MainActivity, body)


                    } else {
                        Toast.makeText( this@MainActivity,"Error during loading of operation", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(call: Call<Operation>, t: Throwable) {

                    Toast.makeText( this@MainActivity,"Error during loading of operation", Toast.LENGTH_SHORT).show()

                }
            })

        }

        opeActu(idInt)




    // lors d'un clic sur ajout/retrait
        var btAdd = findViewById<FloatingActionButton>(R.id.floatingActionButtonAjout)
        btAdd.setOnClickListener{

            var dialog = Dialog(this);
            dialog.setContentView(R.layout.ajout)

            var btAjout = dialog.findViewById<Button>(R.id.btAjout)

            btAjout.setOnClickListener{

                // je récupere les données de mon formulaire
                var montant = dialog.findViewById<TextInputEditText>(R.id.montantAjout).text.toString()
                var tiers = dialog.findViewById<TextInputEditText>(R.id.tiersAJout).text.toString()
                var description = dialog.findViewById<TextInputEditText>(R.id.descriptionAjout).text.toString()
                var modePaiment = dialog.findViewById<TextInputEditText>(R.id.modePaimentAjout).text.toString()

                // si tous les champs sont remplis
                if(!montant.toString().isEmpty() && !tiers.trim().toString().isEmpty() && !description.trim().toString().isEmpty() && !modePaiment.trim().toString().isEmpty() ){

                    val ajout = CompteAJout(idInt, montant.toDouble(), tiers, modePaiment, description);
                    // appel a ma route
                    val ajoutResponse = AuthentificationService.compteRoutes.addArgent(jwtToken.token, ajout)
                    // execution de ma route
                    ajoutResponse.enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {

                            val body = response.body()
                            if (response.code() == 200 && body != null) {
                                // on actualise la page et on quitte
                                SoldeActu(id)
                                opeActu(idInt)
                                dialog.dismiss()

                            } else {
                                Toast.makeText( this@MainActivity,"Erreur durant le chargement", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText( this@MainActivity,"Error during loading of ajout", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
                else{
                    Toast.makeText(this,"Veuillez remplir le formulaire.", Toast.LENGTH_SHORT).show()
                }

            }


            dialog.show()
        }

        var btRetrait = findViewById<FloatingActionButton>(R.id.floatingActionButtonRetrait)

        btRetrait.setOnClickListener{

            var dialog = Dialog(this);
            dialog.setContentView(R.layout.retrait)

            var btRetrait = dialog.findViewById<Button>(R.id.btRetrait)

            btRetrait.setOnClickListener{

                // je récupere les données de mon formulaire
                var montantR = dialog.findViewById<TextInputEditText>(R.id.montantRetrait).text.toString()
                var lieulienR = dialog.findViewById<TextInputEditText>(R.id.tiersRetrait).text.toString()
                var descriptionR = dialog.findViewById<TextInputEditText>(R.id.descriptionRetrait).text.toString()
                var modePaimentR = dialog.findViewById<TextInputEditText>(R.id.montantRetrait).text.toString()

                // si tous les champs sont remplis
                if(!montantR.toString().isEmpty() && !lieulienR.trim().toString().isEmpty() && !descriptionR.trim().toString().isEmpty() && !modePaimentR.trim().toString().isEmpty() ){

                    val retrait = CompteRetrait(idInt, montantR.toDouble(), lieulienR, modePaimentR, descriptionR, 5);
                    val retraitResponse = AuthentificationService.compteRoutes.removeArgent(jwtToken.token, retrait)
                    // execution de ma route
                    retraitResponse.enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {

                            val body = response.body()
                            if (response.code() == 200 && body != null) {

                                // on actualise la page et on quitte
                                SoldeActu(id)
                                opeActu(idInt)
                                dialog.dismiss()

                            } else {
                                Toast.makeText( this@MainActivity,"Erreur durant le chargement", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText( this@MainActivity,"Error during loading of retrait", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
                else{
                    Toast.makeText(this,"Veuillez remplir le formulaire.", Toast.LENGTH_SHORT).show()
                }

            }


            dialog.show()
        }


    }
}


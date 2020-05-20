package com.example.lco_home_workout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.workout_card.*
import java.lang.Exception

class MainActivity : Activity(), View.OnClickListener {

    private lateinit var buttonSignOut: Button
//    private lateinit var textDisplayName: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions
//    private lateinit var linearLayoutParent: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
//        val displayName = auth.currentUser?.displayName
//
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        buttonSignOut = findViewById(R.id.button_sign_out)
//        textDisplayName = findViewById(R.id.text_display_name)
//        linearLayoutParent = findViewById(R.id.linear_layout_parent)
//
//        if(displayName != null) {
//            textDisplayName.text = getString(R.string.welcome_message, displayName,7)
//        }
//        else textDisplayName.text = getString(R.string.welcome_message, auth.currentUser?.email, 7)

        viewManager = LinearLayoutManager(this)
        viewAdapter = CardAdapter(generateCards())
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view_activity_main).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        buttonSignOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.button_sign_out -> {
                try {
                    this.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        mGoogleSignInClient.signOut()
    }

    private fun generateCards(): MutableList<WorkoutInfo> {
        val array: Array<String> = resources.getStringArray(R.array.workouts)
        val workouts: MutableList<WorkoutInfo> = ArrayList()

        for (i in 1..8) {
            when(val workout = array[(0..array.size).random()]) {
                    "push ups" -> {
                        workouts.add(WorkoutInfo(workout, 60, getDrawable(R.drawable.pushups)))
                    }
                    "abdomen stretch" -> {
                        workouts.add(WorkoutInfo(workout, 60, getDrawable(R.drawable.abdomen_stretch)))
                    }
                    "barbell incline" -> {
                        workouts.add(WorkoutInfo(workout, 100, getDrawable(R.drawable.barbell_incline)))
                    }
                    "bench press" -> {
                        workouts.add(WorkoutInfo(workout, 200, getDrawable(R.drawable.bench_press)))
                    }
                    "declined crunches" -> {
                        workouts.add(WorkoutInfo(workout, 60, getDrawable(R.drawable.declined_crunches)))
                    }
                    "curls" -> {
                        workouts.add(WorkoutInfo(workout, 40, getDrawable(R.drawable.dumbell_curls)))
                    }
                    "dumbbell planks" -> {
                        workouts.add(WorkoutInfo(workout, 60, getDrawable(R.drawable.dumbell_planks)))
                    }
                    "hindu stretch" -> {
                        workouts.add(WorkoutInfo(workout, 40, getDrawable(R.drawable.hindu_stretch)))
                    }
                    "leg stretch" -> {
                        workouts.add(WorkoutInfo(workout, 60, getDrawable(R.drawable.leg_stretch)))
                    }
                    "rows" -> {
                        workouts.add(WorkoutInfo(workout, 400, getDrawable(R.drawable.rows)))
                    }
                    "sit ups" -> {
                        workouts.add(WorkoutInfo(workout, 90, getDrawable(R.drawable.situps)))
                    }
                    "triceps press" -> {
                        workouts.add(WorkoutInfo(workout, 60, getDrawable(R.drawable.tricep_press)))
                    }
                    "v stretch" -> {
                        workouts.add(WorkoutInfo(workout, 60, getDrawable(R.drawable.v_stretch)))
                    }
                    "yoga ball raise" -> {
                        workouts.add(WorkoutInfo(workout, 40, getDrawable(R.drawable.yoga_ball_raise)))
                    }
            }
        }
        return workouts
    }
}

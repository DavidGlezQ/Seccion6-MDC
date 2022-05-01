package com.david_glez.seccion6_mdc

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.david_glez.seccion6_mdc.databinding.ActivityScrollingBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewBinding
        binding.fab.setOnClickListener{
            if (binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            } else {
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }

        //findViewById
        /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            if (findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                findViewById<BottomAppBar>(R.id.bottom_app_bar).fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }*/

        binding.bottomAppBar.setNavigationOnClickListener {
            Snackbar.make(binding.root, R.string.message_action_success, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.fab) // Poner el snackbar arriba del fab
                .show()
        }

        binding.contentScrolling.btnSkip.setOnClickListener {
            binding.contentScrolling.cvAd.visibility = View.GONE
        }

        binding.contentScrolling.btnBuy.setOnClickListener {
            Snackbar.make(it, R.string.card_buying, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .setAction(R.string.card_to_go) {
                    Toast.makeText(this, R.string.card_history, Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // Cargamos una imagen por defecto
        loadImageGlide()

        binding.contentScrolling.cbEnablePass.setOnClickListener {
            binding.contentScrolling.tilPassword.isEnabled = !binding.contentScrolling.tilPassword.isEnabled
        }

        //https://redwerk.com/wp-content/uploads/2020/07/Glide_logo.png
        binding.contentScrolling.etUrl.onFocusChangeListener = View.OnFocusChangeListener { _, focused ->
            var errorStr: String? = null
            val url = binding.contentScrolling.etUrl.text.toString()
            if (!focused){

                when {
                    url.isEmpty() -> {
                        errorStr = getString(R.string.card_required)
                    }
                    URLUtil.isValidUrl(url) -> {
                        loadImageGlide(url)
                    }
                    else -> {
                        errorStr = getString(R.string.card_invalid_url)
                    }
                }

                /*if (url.isEmpty()){
                    errorStr = getString(R.string.card_required)
                } else if (URLUtil.isValidUrl(url)){
                    loadImageGlide(url)
                } else {
                    errorStr = getString(R.string.card_invalid_url)
                }*/

            }
            binding.contentScrolling.tilUrl.error = errorStr
        }

        binding.contentScrolling.toggleButton.addOnButtonCheckedListener { _, checkedId, _ ->
            binding.contentScrolling.root.setBackgroundColor(
                when(checkedId){
                    R.id.btnBlue -> Color.BLUE
                    R.id.btnGreen -> Color.GREEN
                    else -> Color.RED
                }
            )
        }
    }

    private fun loadImageGlide(url: String = "https://miro.medium.com/max/1400/1*R8xPe4JHCtjxxPaUMA1MAw.png"){
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //El all puede variar depende del objetivo de la app, en este caso all
            .centerCrop()
            .into(binding.contentScrolling.imgCover)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
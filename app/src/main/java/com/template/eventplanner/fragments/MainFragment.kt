package com.template.eventplanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.template.eventplanner.composenavigation.NavigationModule
import com.template.eventplanner.databinding.FragmentMainBinding
import com.template.eventplanner.fragments.viewmodels.MainViewModel
import com.template.eventplanner.ui.theme.EventPlannerTheme
import com.template.eventplanner.ui.theme.LightPrimaryColor
import com.template.eventplanner.ui.theme.DarkPrimaryColor

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)


        binding.mainComposableFragment.setContent {
            val mainViewModel = viewModel(MainViewModel::class.java)
            EventPlannerTheme {
                val navControllerCompose = rememberAnimatedNavController()
                Column(
                    Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(1f)
                        .background(LightPrimaryColor),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NavigationModule(
                        navController = findNavController(),
                        navControllerCompose = navControllerCompose,
                        lifecycleOwner = this@MainFragment,
                        mainViewModel = mainViewModel,
                    )
                    BottomAppBar(
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        containerColor = DarkPrimaryColor
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .background(DarkPrimaryColor),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            val sizeButton = 60.dp
                            val flagButton =
                                remember { mutableStateListOf(true, false, false, false) }
                            Button(
                                modifier = Modifier.width(sizeButton),
                                enabled = !flagButton[0],
                                onClick = {
                                    for (i in 0 until flagButton.size)
                                        flagButton[i] = false
                                    flagButton[0] = true
                                    mainViewModel.changeModeScreen(0)
                                    mainViewModel.changeVisibleEventListByDate()
                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                content = {
                                    Text(text = "M")
                                })
                            Button(
                                modifier = Modifier.width(sizeButton),
                                enabled = !flagButton[1],
                                onClick = {
                                    for (i in 0 until flagButton.size)
                                        flagButton[i] = false
                                    flagButton[1] = true
                                    mainViewModel.changeVisibleEventListByLiked(true)
                                    mainViewModel.changeModeScreen(1)

                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                content = {
                                    Text(
                                        text = "\uD83E\uDD0D",
                                        textAlign = TextAlign.Center
                                    )
                                })
                            Button(
                                modifier = Modifier.width(sizeButton),
                                enabled = !flagButton[2],
                                onClick = {
                                    for (i in 0 until flagButton.size)
                                        flagButton[i] = false
                                    flagButton[2] = true
                                    mainViewModel.changeVisibleEventListByVisited(true)
                                    mainViewModel.changeModeScreen(2)

                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                content = { Text(text = "✔") })
                            Button(
                                modifier = Modifier.width(sizeButton),
                                enabled = !flagButton[3],
                                onClick = {
                                    for (i in 0 until flagButton.size)
                                        flagButton[i] = false
                                    flagButton[3] = true
                                    mainViewModel.changeVisibleEventListByVisited(false)
                                    mainViewModel.changeModeScreen(3)

                                },
                                colors = ButtonDefaults.buttonColors((Color.Transparent)),
                                content = { Text(text = "Х") })

                        }

                    }
                }
            }
        }
        return binding.root
    }

}



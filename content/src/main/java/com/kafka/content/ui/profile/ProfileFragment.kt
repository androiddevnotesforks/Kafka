package com.kafka.content.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kafka.content.R
import com.kafka.content.databinding.FragmentProfileBinding
import com.kafka.ui_common.base.BaseFragment
import com.kafka.ui_common.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    @Inject lateinit var profileController: ProfileController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            setController(profileController)
        }

        profileController.setData(0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}

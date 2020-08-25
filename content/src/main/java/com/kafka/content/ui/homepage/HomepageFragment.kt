package com.kafka.content.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.data.base.extensions.debug
import com.kafka.content.R
import com.kafka.content.ui.query.ArchiveQueryViewModel
import com.kafka.content.ui.query.SearchAction
import com.kafka.ui_common.base.BaseFragment
import com.kafka.ui_common.extensions.addScrollbarElevationView
import com.kafka.ui_common.extensions.showSnackbar
import com.kafka.ui_common.navigation.Navigation
import com.kafka.ui_common.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_homepage.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

@AndroidEntryPoint
class HomepageFragment : BaseFragment() {
    private val archiveQueryViewModel: ArchiveQueryViewModel by viewModels()
    private val homepageViewModel: HomepageViewModel by viewModels()
    private val navController by lazy { findNavController() }

    @Inject lateinit var homepageController: HomepageController
    private val homepageActioner = Channel<HomepageAction>(Channel.BUFFERED)
    private val searchActioner = Channel<SearchAction>(Channel.BUFFERED)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            setController(homepageController)
            addScrollbarElevationView(shadowView)
            homepageController.homepageActioner = homepageActioner
            homepageController.searchActioner = searchActioner
        }

        archiveQueryViewModel.liveData.observe(viewLifecycleOwner, Observer {
            homepageController.setSearchViewState(it)
            it.error?.let { view.showSnackbar(it.message) }
        })

        homepageViewModel.liveData.observe(viewLifecycleOwner, Observer {
            debug { "Homepage updated" }
            homepageController.setHomepageViewState(it)
        })

        lifecycleScope.launchWhenCreated {
            searchActioner.consumeAsFlow().collect { action ->
                when (action) {
                    is SearchAction.ItemDetailAction -> navController.navigate(Navigation.ItemDetail(action.item.itemId))
                    is SearchAction.ItemDetailWithSharedElement -> {
                        action.view.transitionName = action.item.itemId
                        navController.navigate(
                            Navigation.ItemDetail(action.item.itemId),
                            FragmentNavigatorExtras(action.view to action.item.itemId)
                        )
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            homepageActioner.consumeAsFlow().collect { action ->
                when (action) {
                    is HomepageAction.SelectTag -> {
                        debug { "action $action" }
                        homepageViewModel.selectTag(action.tag)
                        archiveQueryViewModel.submitQuery(action.tag.searchQuery)
                    }
                    is HomepageAction.OpenSearchFragment -> {
                        parentFragment?.findNavController()?.navigate(Navigation.Search)
                    }
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            homepageActioner.send(HomepageAction.SelectTag(homepageViewModel.getSelectedTag()))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }
}

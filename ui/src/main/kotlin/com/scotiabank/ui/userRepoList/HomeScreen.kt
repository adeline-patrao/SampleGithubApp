package com.scotiabank.ui.userRepoList

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.scotiabank.domain.model.Repo
import com.scotiabank.domain.model.UserRepo
import com.scotiabank.ui.NavigationItem
import com.scotiabank.ui.R
import com.scotiabank.ui.util.UiStateWrapper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    var userId by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val userRepoUiState by viewModel.userRepoUiState.collectAsStateWithLifecycle()
    val userRepoState by viewModel.userRepoUiState.collectAsStateWithLifecycle()

    Scaffold(topBar = { TopAppBar(title = { Text(text = "SampleGitHubApp") }) }) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                modifier = modifier,
                userId,
                onChange = { userId = it },
                onSearchClicked = {
                    if (userId.isNotEmpty()) {
                        keyboardController?.hide()
                        viewModel.getUserRepo(userId)
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            UiStateWrapper(uiState = userRepoUiState, onLoading = {}) { userRepo ->

                UserInfo(modifier, userRepo)
                RepoList(modifier, userRepo.repos ?: emptyList(), navController, userRepo.starBadgeEnabled)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepoList(modifier: Modifier, repos: List<Repo>, navController: NavController, starBadgeEnabled: Boolean) {
    val coroutineScope = rememberCoroutineScope()
    AnimatedContent(targetState = repos, label = "RepoList", transitionSpec = {
        (fadeIn() + slideInVertically(animationSpec = tween(1000),
            initialOffsetY = { fullHeight -> fullHeight })).togetherWith(
            exit = ExitTransition.None
        )
    }) { targetRepos ->
        LazyColumn(modifier = modifier.padding(10.dp)) {
            items(targetRepos) { repo ->
                OutlinedCard(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .animateItemPlacement(tween(600))
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(2.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.cardColors(),
                    onClick = {
                        coroutineScope.launch {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "repo",
                                value = repo
                            )
                            navController.navigate(NavigationItem.Detail.withArgs(starBadgeEnabled.toString()))
                        }
                    }
                ) {
                    RepoListItem(modifier, repo)
                }
            }
        }
    }
}

@Composable
fun RepoListItem(
    modifier: Modifier = Modifier,
    repo: Repo,
) {
    Column(modifier = modifier) {
        repo.name?.let {
            Text(
                text = it, modifier = modifier.padding(10.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.ExtraBold,
                style = TextStyle(fontSize = 16.sp)
            )
        }
        repo.description?.let {
            Text(
                text = it, modifier = modifier.padding(10.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}

@Composable
fun UserInfo(modifier: Modifier, userRepo: UserRepo) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(targetState = userRepo, label = "",
            transitionSpec = {
                (fadeIn() + slideInVertically(animationSpec = tween(1000),
                    initialOffsetY = { fullHeight -> fullHeight })).togetherWith(
                    exit = ExitTransition.None
                )
            }
        ) { userRepo ->
            AsyncImage(
                modifier = modifier
                    .height(100.dp)
                    .width(100.dp), model = userRepo.avatarUrl, contentDescription = ""
            )
        }

        AnimatedContent(targetState = userRepo.userName ?: "", label = "",
            transitionSpec = {
                (fadeIn() + slideInVertically(animationSpec = tween(1000),
                    initialOffsetY = { fullHeight -> fullHeight })).togetherWith(
                    exit = ExitTransition.None

                )
            }) { userName ->
            Text(
                modifier = modifier,
                text = userName,
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 16.sp)
            )

        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    userId: String,
    onChange: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        SearchTextField(
            value = userId,
            onChange = onChange,
            modifier = modifier.fillMaxWidth(0.7f),
            onSearchClicked = onSearchClicked
        )
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            onClick = onSearchClicked,
            enabled = userId.isNotEmpty(),
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
        ) {
            Text(stringResource(R.string.homescreen_button_search_text))
        }
    }
}

@Composable
fun SearchTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.homescreen_search_textfield_label),
    onSearchClicked: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onSearchClicked() }),
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None,
    )
}
#Sample Github Project

##Functional Requirements
1. The app should accept a github user's id as input and display the specified user's avatar and name.
2. For each public repository owned by the user, the name and description are shown in a scrollable list.
3. When a repository is selected, user should go to a detail screen which should display the details regarding that
     specific repo.
4. Display the total number of forks across all the user's repos on detail screen. If the total number of forks exceed
   count 5000 then we want to have a star badge(which can be annotated by simple red/gold color on the text).


#Assumption
1. For the #4 requirement, I have calculated the totalforks as sumOf of forks of all the repos. We can modify easily in the UserRepo class

Multi Module Architecture
The app architecture has three layers: a data layer, a domain layer and a UI layer.
1. Data Layer : 
   1. Responsible for loading the RemoteDataSource into the Repository which is the Single source of truth
   2. RemoteDataSource relies on the GitHubApi
   3. Contains the DTO (Data transfer objects)
2. Domain Layer:
   1. Contains use cases. These are classes which have a single invocable method (operator fun invoke) containing business logic.
3. UI Layer
   1. UI elements built using Jetpack Compose 
   2. ViewModels : The ViewModels receive streams of data from use cases and repositories, and transforms them into UI state

#Libraries & Tools
1. Kotlin Coroutines: For efficient asynchronous programming.
2. Kotlin Flow: Managing async data streams.
3. Hilt: For injecting dependencies.
4. Retrofit: Handling APIs.
5. Jetpack Compose UI & Compose-Navigation: UI & navigation.
6. Coil-Compose: Loading images.

#Known Issues
1. The animation to load the UserInfo and List of repo's does not work for the first time (something to do with the initial load of the UIState)
2. In landscape mode, the list of repo's is cut off. Possible solution is to have all the items i.e. SearchBar, UserInfo and RepoList inside a LazyColumn which will make the whole content scrollable. 
    LazyColumn {
          item { SearchBar}
          item{ UserInfo}
          items { RepoListItem}
    }
3. I have hardcoded the all the errors into a generic error. Given the time, I would have different Errors like UserNotFound, Network error, Generic error

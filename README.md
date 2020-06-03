# Wiki Articles

The app fetches the current location of the device and gets nearby English language Wikipedia articles.



# Code structure


#### Clean Architecture
The main purpose of this type of architecture is the separation of concerns by keeping business rules separate from how the data is obtained and presented, thus, making it possible to test it without depending on any external element.
Each layer will use its own data model so that it is completely independent, there will be mappers between layers to accomplish this.


Here is a visual representation of it:

![image](https://user-images.githubusercontent.com/18377996/74106103-5aca9b00-4b6c-11ea-94c5-7683dab937a7.png)


#### Presentation Layer
In this layer is where the logic related to views and animation lays. **M**odel **V**iew **V**iew**M**odel (MVVM from now on) is used which allows us to keep UI logic completely separate from any other operations, such us retrieving data. There must not be any logic, other than view logic, in Views.
**ViewModels** will use **UseCases** internally. UseCases perform their logic in a separate thread so we can be sure that we won't be doing any heavy operation on the main thread.

#### Domain Layer
As mentioned before, this is where business rules are, all business logic should happen in this layer. Here is where the UseCases are created and also where the Repository interfaces are defined, this includes data model contracts.
This layer must not have any Android dependencies. All external components use interfaces when connecting to business objects.
A **UseCase** represents an action that we can do with our application (e.g. "Get article details") and its main function is to orchestrate the business logic.

#### Data Layer
All data needed for the application comes through this layer by implementing the **Repository** interfaces exposed in the Domain Layer, external objects will be mapped to the ones expected by the interface.


#### Third Party Libraries
[DataBinding](https://developer.android.com/topic/libraries/data-binding) - allows you to bind UI components in your layouts to data sources.

[RxJava2](https://github.com/ReactiveX/RxJava/tree/2.x) is heavily used across the app for threading/asynchronous, event-based tasks.

[Retrofit2](https://square.github.io/retrofit/), [Okhttp](https://square.github.io/okhttp/) & [Gson](https://github.com/google/gson) are used for constructing the REST API.

[Stetho](http://developer.android.com/tools/testing-support-library/index.html#setup) - as a debug bridge.

[Glide](https://github.com/bumptech/glide) - as image loader.

##### Notes
There are unit tests written for Use case. For times sake, I have not added tests for all classes.
Also, I'm using fake Directions SDK because it is not free.

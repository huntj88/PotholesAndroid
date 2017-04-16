# PotholesAndroid
android app to use my potholes api

with this app the objective was the keep the code as modular and testable as possible.

I followed the Model-View-Intent design pattern. Its kind of similiar to MVP.
To learn more about it check out this link
http://hannesdorfmann.com/android/mosby3-mvi-1

I've structured my project so that the only code in the activity is code related to the UI

all of the bussiness logic has been outsourced to the presenter and two models i have.

I made my models Immutable containers of data. 
This means that you can have methods that tell you about the data, but the data cannot be changed.

Instead, when you need to manipulate the data you instantiate a new model, and feed it the updated values in the constructor.

This was done to prevent race conditions and to make my code thread safe.

Each model should only contain the data for one aspect of something. keep it small.

by abstracting the business logic away from the "view" i should be able to implement tests fairly easily.



things i still need to implement.

make map starts big, but resize when using the recyclerview and scrolling down.
realm
rxjava
testing





what i want 

getPotholesByLocation()
{
  using JavaRx make a realm obersvable, and api-retrofit obervable
  load results from realm and display them immediately
  load results for api call, and use the rxJava merge operator (or similar, find some way to mix results)
  use merge operator the mix realm and retrofit results. display new potholes, update old ones if changed
  update potholes in realm
  
  // realm should just load all of the markers it has and display them
  // api call should only request the potholes around whenerer you are looking, and update the realm results as it gets them
  // save the updates to realm
  // if zoomed out use google maps marker clusting to combine
  
  
}

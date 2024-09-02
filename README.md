The goal of this test project application is to gather the list of Foursquare venues that are specialized in coffee or cocktails close to your current location (within a certain radius) and display them in a grid. The list should be filtered by price (options: $, $$, $$$, $$$$) using a drop down component and availability
(Open Now) using a toggle.

This project has two flavors - Coffee and Cocktails

frameworks used in the project:

Hilt - Provides a standard way to do in-app DI, providing containers for each Android component in the project and managing the container lifecycle automatically. For use the Dagger was also used.

Retrofit - In addition to allowing a simple implementation and being one of the most used frameworks on the market, with OkHttp it is easy, for example, to intercept the request and change it the way you need it.

JetPack Compose - Was used because it has a great reduction of code, the Compose library is not coupled to the operating system, as with current components, it is compatible with legacy components (xml) and compose was designed in a way that we can build our interfaces with a system of small, reusable, self-contained code blocks.

Glide - is a composable that executes an image request asynchronously and renders the result. It supports the same arguments as the standard Image composable and additionally, it supports setting placeholder/error/fallback painters and onLoading/onSuccess/onError callbacks. Here's an example that loads an image with a circle crop, crossfade, and sets a placeholder.

Architecture:

![arch](https://github.com/user-attachments/assets/26bc3f55-68cb-467d-b079-dbd9e5215434)

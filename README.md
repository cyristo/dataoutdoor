# Data externalization for testing or other usage. 
Data Outdoor enables you to simply expose any data row of any Excel files as a REST resource. 

Data Outdoor is based on 4 concepts:
* data source : URI to an Excel file
* data category : name of the Excel sheet
* data id : value of the first column
* dataset : the row referenced by the data id, in the data cataegory of the data source

Once Data Outdoor is configured and launched, you can access to any dataset through its REST resource: 
http://<yourhost>:<yourport>/dataoutdoor/<yourdatasource>/<yourdatacategory>/<yourdataid>

## How to install ?
1. Donwload the distrib directory
2. rename dataoutdoor.<version>.jar-with-dependencies.zip to rename dataoutdoor.<version>.jar-with-dependencies.zip
3. edit the dataoutdoor.properties to configure your datasources and target URI
4. launch Data Outdoor : java -jar dataoutdoor.<version>.jar-with-dependencies.zip

## BDD use case : instead of having data inside the test scenario, use Data Outdoor to reference an external dataset. 

Without Data Outdoor :
* Given the country name is France
* And the country population is 64768389
* And the country area is 547030
* And the country GDP is 2.739 Trillion
* When I do something with that country
* Then I should get this result

With Data Outdoor:
* Given the country name is France (-> here your fixture code will access the dataset)
* When I do something with that country
* Then I should get this result

## Other use cases

You can use Data Outdoor as a java framework. In that case, many other nice features are implemented as well.

See the JUnit classes to get the documentation.

# Data externalization for testing or other usage. 
Feed Data Outdoor with the data source (path to Excel file) and the dataset reference (either the row number or the cell value of the ID  column), then you will get the dataset (Excel row) in the form of a hashmap (the first row of the Excel sheet as the keys). 

You can target a specific sheet of a multi sheet Excel file.

## BDD use case : instead of having data inside the test scenario, use Data Outdoor to reference an external dataset. 

Without Data Outdoor :
* Given the country name is France
* And the country population is 64768389
* And the country area is 547030
* And the country GDP is 2.739 Trillion
* When I do something with that country
* Then I should get this result

With Data Outdoor:
* Given the country name is France (=> here your Fixture step will get the data set)
* When I do something with that country
* Then I should get this result

## Other use cases

You can use Data Outdoor to simply get every Excel rows in a Collection of Object[], so that you can inject that Collection in your parametrized test class. You can set a filter on the ID column, to get a Colleciton that match only your criteria.

At the opposite, you can use Data Outdoor to access a single Cell by its Excel reference.

See the JUnit classes to get the documentation.

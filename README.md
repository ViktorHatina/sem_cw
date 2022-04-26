# Coursework Software Engineering Methods

Group 4! Let's Goooo

* Develop Build Status ![Github Workflow Status (branch)](https://img.shields.io/github/workflow/status/ViktorHatina/sem_cw/A%20workflow%20for%20our%20CW/develop?style=flat-square)
* Master Build Status ![workflow](https://github.com/ViktorHatina/sem_cw/actions/workflows/main.yml/badge.svg)
* License [![LICENSE](https://img.shields.io/github/license/ViktorHatina/sem_cw.svg?style=flat-square)](https://github.com/ViktorHatina/sem_cw/blob/master/LICENSE)
* Release [![Releases](https://img.shields.io/github/release/ViktorHatina/sem_cw/all.svg?style=flat-square)](https://github.com/ViktorHatina/sem_cw/releases)


# Product Backlog
| User Story                                                                                                                                                                                                                       | Associated Tasks                                         | Priority | Work Estimate |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------|----------|---------------|
| As a user, I want to be working with the data I have provided                                                                                                                                                                    | Get SQL database into a usable format                    | 1        | 2             |
| As a user, I want to be able to specify an area (and optionally a number n), and be given a list of either all, or only the first n, countries, cities, or capital cities in that area, sorted by population in descending order | Implement code to output report specified by user inputs | 2        | 3             |
| As a user, I want to be able to specify an area and be given the total population of the area, the total population of all cities in the area, and the leftover population which does not stem from cities                       | Implement code to output report specified by user inputs | 3        | 3             |
| As a user, I want to be able to specify an area and be given a numerical value of its population                                                                                                                                 | Implement code to output report specified by user inputs | 4        | 3             |
| As a user, I want to know the number of people who speak each of the five required languages and what that number is as a percentage of the world population                                                                     | Implement code to output report specified by user inputs | 5        | 3             |
| As a user, I want to receive a stable application                                                                                                                                                                                | Implement unit tests                                     | 6        | 1             |
| (As Above)                                                                                                                                                                                                                       | Implement integration tests                              | 6        | 1             |




The organisation has asked for the following reports to be generated:

- All the countries in the world organised by largest population to smallest.
- All the countries in a continent organised by largest population to smallest.
- All the countries in a region organised by largest population to smallest.
- The top `N` populated countries in the world where `N` is provided by the user.
- The top `N` populated countries in a continent where `N` is provided by the user.
- The top `N` populated countries in a region where `N` is provided by the user.
- All the cities in the world organised by largest population to smallest.
- All the cities in a continent organised by largest population to smallest.
- All the cities in a region organised by largest population to smallest.
- All the cities in a country organised by largest population to smallest.
- All the cities in a district organised by largest population to smallest.
- The top `N` populated cities in the world where `N` is provided by the user.
- The top `N` populated cities in a continent where `N` is provided by the user.
- The top `N` populated cities in a region where `N` is provided by the user.
- The top `N` populated cities in a country where `N` is provided by the user.
- The top `N` populated cities in a district where `N` is provided by the user.
- All the capital cities in the world organised by largest population to smallest.
- All the capital cities in a continent organised by largest population to smallest.
- All the capital cities in a region organised by largest to smallest.
- The top `N` populated capital cities in the world  where `N` is provided by the user.
- The top `N` populated capital cities in a continent where `N` is provided by the user.
- The top `N` populated capital cities in a region where `N` is provided by the user.
- The population of people, people living in cities, and people not living in cities in each continent.
- The population of people, people living in cities, and people not living in cities in each region.
- The population of people, people living in cities, and people not living in cities in each country.

Additionally, the following information should be accessible to the organisation:

- The population of the world.
- The population of a continent.
- The population of a region.
- The population of a country.
- The population of a district.
- The population of a city.

Finally, the organisation has asked if it is possible to provide the number of people who speak the following the following languages from greatest number to smallest, including the percentage of the world population:

- Chinese.
- English.
- Hindi.
- Spanish.
- Arabic.

---

> 32 requirements of 32 have been implemented, which is 100%.


| ID  | Name                                                                                                                                                                        | Met | 
|:---:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----|
|  1  | All the countries in the world organised by largest population to smallest.                                                                                                 | No  |
|  2  | All the countries in a continent organised by largest population to smallest.                                                                                               | No  |     
|  3  | All the countries in a region organised by largest population to smallest.                                                                                                  | No  |     
|  4  | The top N populated countries in the world where N is provided by the user.                                                                                                 | No  |     
|  5  | The top N populated countries in a continent where N is provided by the user.                                                                                               | No  |     
|  6  | The top N populated countries in a region where N is provided by the user.                                                                                                  | No  |     
|  7  | All the cities in the world organised by largest population to smallest.                                                                                                    | No  |     
|  8  | All the cities in a continent organised by largest population to smallest.                                                                                                  | No  |
|  9  | All the cities in a region organised by largest population to smallest.                                                                                                     | No  |
| 10  | All the cities in a country organised by largest population to smallest.                                                                                                    | No  |     
| 11  | All the cities in a district organised by largest population to smallest.                                                                                                   | No  |    
| 12  | The top N populated cities in the world where N is provided by the user.                                                                                                    | No  |     
| 13  | The top N populated cities in a continent where N is provided by the user.                                                                                                  | No  |     
| 14  | The top N populated cities in a region where N is provided by the user.                                                                                                     | No  |     
| 15  | The top N populated cities in a country where N is provided by the user.                                                                                                    | No  |     
| 16  | The top N populated cities in a district where N is provided by the user.                                                                                                   | No  |     
| 17  | All the capital cities in the world organised by largest population to smallest.                                                                                            | No  |
| 18  | All the capital cities in a continent organised by largest population to smallest.                                                                                          | No  |
| 19  | All the capital cities in a region organised by largest to smallest.                                                                                                        | No  |     
| 20  | The top N populated capital cities in the world where N is provided by the user.                                                                                            | No  |     
| 21  | The top N populated capital cities in a continent where N is provided by the user.                                                                                          | No  |     
| 22  | The top N populated capital cities in a region where N is provided by the user.                                                                                             | No  |     
| 23  | The population of people, people living in cities, and people not living in cities in each continent.                                                                       | No  |    
| 24  | The population of people, people living in cities, and people not living in cities in each region.                                                                          | No  |   
| 25  | The population of people, people living in cities, and people not living in cities in each country.                                                                         | No  |  
| 26  | The population of the world.                                                                                                                                                | No  |     
| 27  | The population of a continent.                                                                                                                                              | No  |     
| 28  | The population of a region.                                                                                                                                                 | No  |    
| 29  | The population of a country.                                                                                                                                                | No  |  
| 30  | The population of a district.                                                                                                                                               | No  |  
| 31  | The population of a city.                                                                                                                                                   | No  |  
| 32  | Provide the Number of People who speak Chinese, English, Hindi, Spanish and Arabic, from the Greatest Number to Smallest, including the Percentage of the World Population. | No  |

---
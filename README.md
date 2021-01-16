# GMDB API
## API Specifications
**Resource Summary**
| URI                                                | HTTP Method |   HTTP Status   | Description                                           |
|----------------------------------------------------|-------------|-----------------|-------------------------------------------------------|
| gmdb.com/api/movies          | GET         |   200 OK		 | Returns the list of  movie titles|
| gmdb.com/api/movies/{title}  | GET         |   200 OK        | Return the details for that movie                    |
| gmdb.com/api/movies/{title}    | GET         |   400 BAD REQUEST  | Returns error message when movie is not found    |

---
**GET /api/movies**

Response Body:
```json
{
	"data":{
		"movieTitles":[
		"Titanic",
		"Superman",
		 "Avatar",
		 "Avengers"
		]
	},
	"errorMessages":[
		"errors",
		"errors"
	]		
}
```

---

**GET /api/movies/{title}**

Response Body:
```json
{
	"data":{
	"movieDetail":{
		"title" : "string",
		"director" : "string",
		"actors" : "string",
		"releaseYear" : "string",
		"description" : "string",
		"rating" : number		
		}
	},
	"errorMessages":[
		"Movie title not found",
		"errors"
	]	
}
```
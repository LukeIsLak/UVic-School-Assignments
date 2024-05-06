let g = "https://docs-assets.developer.apple.com/published/218def98e6/267c8fd4-12a4-4fbb-89ef-ed88d909b9f1.png";
let pg = "https://docs-assets.developer.apple.com/published/f44edd2a0a/9a2a66de-b42e-447b-8894-89182d4b0b08.png";
let pg13 = "https://docs-assets.developer.apple.com/published/8cab854be4/c9b697bd-27a2-4b98-8330-38842529f91a.png";
let nc17 = "https://docs-assets.developer.apple.com/published/66836a12ac/d8e30ec4-21ce-41fc-a97c-f7067b49da4a.png";
let r = "https://docs-assets.developer.apple.com/published/f3448319da/9d1c255f-eae3-42a1-a557-dbf5f33eaaa1.png";
let unrated = "https://docs-assets.developer.apple.com/published/522c8ab8e3/a34a2e4f-be16-4d5f-b5fd-9922473c771b.png";

function displayResults(data) {
	
	$("#resultTitle").html(data.Title);
	$("#resultGenre").html(data.Genre);
	$("#resultDirector").html(data.Director);
	$("#resultStars").html(data.Actors);
	$("#resultTime").html(data.Runtime);
	$("#resultBoxOffice").html(data.BoxOffice);
	let rating = data.Rated;
	$("#resultRating").html(rating);

	let ratingImageURL = unrated;
	if (rating === "G") {
		ratingImageURL = g;
	} else if (rating === "PG") {
		ratingImageURL = pg;
	} else if (rating === "PG-13") {
		ratingImageURL = pg13;
	} else if (rating === "NC-17") {
		ratingImageURL = nc17;
	} else if (rating === "R") {
		ratingImageURL = r;
	} else {
		ratingImageURL = unrated;
	}
	
	$("#ratingImg").attr("src", ratingImageURL);
}




function submit() {
	$("#output").show();
	
	let title = $("#movieTitle").val();
	let year = $("#movieYear").val();
	let key = "1a49f356";
	
	let url = "https://www.omdbapi.com/?apikey="
	url += key;
	url += "&t=" + title
	url += "&y=" + year;
	
	$.get(url, function(data) {
		document.getElementById("raw").innerHTML = JSON.stringify(data);
		displayResults(data);
		
	});
}
let mobile_no = sessionStorage.getItem("mobile");


var baseurl = "http://localhost:8081/api";

fetch(baseurl + "/restaurentuser/getrestaurantuser/" + mobile_no)
	.then(res => res.json())
	.then((data) => {
		console.log(data);
		
	})
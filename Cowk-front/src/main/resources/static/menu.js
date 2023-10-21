let restaurant_id = sessionStorage.getItem('restaurant_id');
let table_no = sessionStorage.getItem("table_no");


const res_name = document.getElementById("restaurent_name");
const table_number = document.getElementById("table_no");
const tables = document.getElementsByName("table_no");
const menu_items = document.getElementById("menu-items");
const billBtn = document.getElementById("bill");

var baseurl = "http://localhost:8081/api";

table_number.innerHTML = table_no;

//console.log(tables.length);

for (var i = 0; i < tables.length; i++) {
	tables[i].value = table_no;
}

get_items();

async function get_items() {
	fetch(baseurl + "/restaurant/getrestaurant/" + restaurant_id)
		.then(res => res.json())
		.then((data) => {
			//console.log(data);
			res_name.innerHTML = data.name;
		})


	fetch(baseurl + "/menu/getmenu/" + restaurant_id)
		.then(res => res.json())
		.then((data) => {
			//console.log(data);
			items = data.items;
			let newLiTag = "";
			//console.log(items)
			items.forEach((item) => {
				//console.log(element)
				//console.log(item.name)
				newLiTag += `<div class="food-menu">
					<div class="head-menu">
						<div class="">
							<span class=${item.type}></span>
							<span>${item.name}</span>
						</div>
						<div class="gray2"> ₹<span>${item.final_price}</span></div>
					</div>
					<div class="foot-menu">
						<button class='bill-btn addBtnClass' value=${item.id}>
							Add
						</button>
						<div class='d-none'>
							<button class="decr" id="decr" value=${item.id}>
								-
							</button>
							<input type="number" readonly="" class="food-val" value="0">
							<button class="incr" id="incr" value=${item.id}>
								+
							</button>
						</div>
					</div>
				</div>`;
			});
			menu_items.innerHTML = newLiTag;

		})
		.then(data=>{Button_Functions();})
}

item_id = {};

function Button_Functions() {
	var decr = document.getElementsByClassName('decr');
	var incr = document.getElementsByClassName('incr');
	var addBtnClass = document.getElementsByClassName('addBtnClass');

	console.log(addBtnClass.length)


	for (let i = 0; i < addBtnClass.length; i++) {
		addBtnClass[i].nextElementSibling.children[1].disabled = true;

		addBtnClass[i].addEventListener('click', () => {
			addBtnClass[i].classList.add('d-none');
			addBtnClass[i].nextElementSibling.classList.remove('d-none');
			addBtnClass[i].nextElementSibling.children[1].value = 1;
			item_display(addBtnClass[i].value, addBtnClass[i].nextElementSibling.children[1].value);
			addBtnClass[i].nextElementSibling.children[1].disabled = false;
		});
		decr[i].addEventListener('click', () => {
			decr[i].nextElementSibling.value--;
			item_display(decr[i].value, decr[i].nextElementSibling.value);
			if (decr[i].nextElementSibling.value == 0) {
				addBtnClass[i].classList.remove('d-none');
				addBtnClass[i].nextElementSibling.classList.add('d-none');
				addBtnClass[i].nextElementSibling.children[1].disabled = false;
			}
		});
		incr[i].addEventListener('click', () => {
			incr[i].previousElementSibling.value++;
			item_display(incr[i].value, incr[i].previousElementSibling.value);
			//console.log(incr[i].previousElementSibling.value)
		});
	}
}



function item_display(i, j) {
	item_id[i] = j;
	items_ = []
	//console.log(item_id);
	const item_num_cart = document.getElementById("item_num_cart");
	const total_price_cart = document.getElementById("total_price_cart");
	//console.log(Object.keys(item_id).length)
	let no_of_items = 0;
	let total_bill = 0;
	total_price_cart.innerHTML = total_bill
	Object.entries(item_id).forEach(([key, value]) => {
		fetch(baseurl + "/item/getitem/" + key)
			.then(res => res.json())
			.then((data) => {
				for (i = 0; i < value; i++) {
					items_.push(data);
					total_bill += data.final_price;
					total_price_cart.innerHTML = total_bill
				}
			})
		no_of_items = no_of_items + parseInt(value);
		item_num_cart.innerHTML = no_of_items;
	})
	sessionStorage.setItem("items",items_)
	//console.log(total_bill)
}

billBtn.onclick = () => {

	window.location.href = "/bill";
}

console.log(sessionStorage.getItem("items"))


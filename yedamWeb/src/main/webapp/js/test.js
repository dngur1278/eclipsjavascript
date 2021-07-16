/**
 * ajax.js : Asynchronous JavaScript And Xml 
 */
let table = document.createElement('table');
table.setAttribute('border', '1');
			
function ajaxFnc() {

	let xhtp = new XMLHttpRequest();
	xhtp.open("get", "EmpListServlet");
	xhtp.send();
	xhtp.onreadystatechange = function() {
		if (xhtp.readyState == 4 && xhtp.status == 200) {
			let data = JSON.parse(xhtp.responseText);
			console.log(data);
			showEmpList(data);
		}
	}
}
ajaxFnc();


document.getElementById('btnDel').addEventListener('click', frm_delete1);

function frm_delete1() {
	console.log(this.getAttribute('id'));
}

// 리스트 보여주는 부분
function showEmpList(data) {
	let cap = document.createElement('caption');
	cap.appendChild(document.createTextNode("사원리스트"));
	
	table.appendChild(cap);
	createHead();
	// 전체 데이터를 가져와서 반복문으로 (필드명 + 필드값)
	for (let row of data) {
		let tr = document.createElement('tr');
		tr.setAttribute('id', row.employee_id);
	 	tr.onclick = trClick;
		for (let field in row) {
			let td = document.createElement('td');
			let text = document.createTextNode(row[field]);
			td.style.height = "30px";
			td.style.width= "100px";
			td.style.textAlign = 'center';
			td.appendChild(text);
			tr.appendChild(td);
		}
		table.appendChild(tr);
	}
	document.getElementById('show').appendChild(table);
}

function createHead() {
	let title = ['id', 'first_name', 'last_name', 'email', 'hire_date'];
	let tr = document.createElement('tr');
	for (let field of title) { // 배열일 경우 for ... of
		let th = document.createElement('th');
		let text = document.createTextNode(field);
		th.appendChild(text);
		tr.appendChild(th);
	}
	table.appendChild(tr);
}



// 저장 버튼 누르면 전송
function frm_submit(e) {
	if(frm.eid.value == ""){
		alert("아이디 입력확인");
		frm.eid.focus();
		return;
	}
	
	if(frm.first_name.value == ""){
		alert("성 입력확인");
		frm.first_name.focus();
		return;
	}
	
	
	// last_name, email, hire_date 체크하기
	// frm.submit()이 재대로 들
	let xhtp = new XMLHttpRequest();
	const id = document.querySelectorAll('input[name="eid"]')[0].value;
	const fn = document.querySelectorAll('input[name="first_name"]')[0].value;
	const ln = document.querySelectorAll('input[name="last_name"]')[0].value;
	const em = document.querySelectorAll('input[name="email"]')[0].value;
	const hd = document.querySelectorAll('input[name="hire_date"]')[0].value;
	const param = 'eid='+id+'&first_name='+fn+'&last_name='+ln+'&email='+em+'&hire_date='+hd;

	xhtp.open('get', 'RegisterServlet?' + param);
	xhtp.send();
	xhtp.onreadystatechange = function() {
		if (xhtp.readyState == 4 && xhtp.status == 200) {
			
			//{id:?, first_name:?, last_name:?, email:?, hire_date:?}
			const data = JSON.parse(xhtp.responseText);
			/*data.id;
			data.first_name;
			data.last_name;
			data.email;
			data.hire_date;*/
			
			let tr = document.createElement('tr');
			for (let field in data) {
				let td = document.createElement('td');
				let text = document.createTextNode(data[field]);
				td.style.height = "30px";
				td.style.width= "100px";
				td.style.textAlign = 'center';
				td.appendChild(text);
				tr.appendChild(td);
			}
			document.querySelector('table').appendChild(tr);
		}
	}
} // end - frm_submit()


function trClick() {
 	const eid = this.getAttribute('id');
	console.log(eid);
	const xhtp = new XMLHttpRequest();
	xhtp.open('get', 'EmployeeServlet?eid='+eid);
	xhtp.send();
	xhtp.onreadystatechange = function() {
		if (xhtp.readyState == 4 && xhtp.status == 200) {
			const data = JSON.parse(xhtp.responseText);
			console.log(data);
			document.querySelector('input[name="eid"]').value = data.employee_id;
			document.querySelector('input[name="first_name"]').value = data.first_name;
			document.querySelector('input[name="last_name"]').value = data.last_name;
			document.querySelector('input[name="email"]').value = data.email;
			document.querySelector('input[name="hire_date"]').value = data.hiredate;
			
		}
	}
} // end - trClick()


// 수정버튼기능
function frm_update() {
	let xhtp = new XMLHttpRequest();
	const id = document.querySelectorAll('input[name="eid"]')[0].value;
	const fn = document.querySelectorAll('input[name="first_name"]')[0].value;
	const ln = document.querySelectorAll('input[name="last_name"]')[0].value;
	const em = document.querySelectorAll('input[name="email"]')[0].value;
	const hd = document.querySelectorAll('input[name="hire_date"]')[0].value;
	const param = 'eid='+id+'&first_name='+fn+'&last_name='+ln+'&email='+em+'&hire_date='+hd;

	xhtp.open('get', 'ModifyServlet?' + param);
	xhtp.send();
	xhtp.onreadystatechange = function() {
		if (xhtp.readyState == 4 && xhtp.status == 200) {
			
			//{id:?, first_name:?, last_name:?, email:?, hire_date:?}
			console.log(xhtp.responseText);
			const data = JSON.parse(xhtp.responseText);
			//data.employeeId 필드를 사용해서 테이블에서 tr의 id값이 같은 요소.
			const findTr = document.getElementById(data.employee_id);	// tr을 찾으려고

			findTr.childNodes[1].childNodes[0].nodeValue = data.first_name;	
			findTr.childNodes[2].childNodes[0].nodeValue = data.last_name;
			findTr.childNodes[3].childNodes[0].nodeValue = data.email;
			findTr.childNodes[4].childNodes[0].nodeValue = data.hiredate;	
		}
	}
}	// end - frm_update()


function frm_delete() {
	let xhtp = new XMLHttpRequest();
	const id = document.querySelectorAll('input[name="eid"]')[0].value;
	const fn = document.querySelectorAll('input[name="first_name"]')[0].value;
	const ln = document.querySelectorAll('input[name="last_name"]')[0].value;
	const em = document.querySelectorAll('input[name="email"]')[0].value;
	const hd = document.querySelectorAll('input[name="hire_date"]')[0].value;
	const param = 'eid='+id+'&first_name='+fn+'&last_name='+ln+'&email='+em+'&hire_date='+hd;

	xhtp.open('get', 'DeleteServlet?' + param);
	xhtp.send();
	
	xhtp.onreadystatechange = function() {
		if (xhtp.readyState == 4 && xhtp.status == 200) {
			document.getElementById(id).remove();
		}
	}	
}
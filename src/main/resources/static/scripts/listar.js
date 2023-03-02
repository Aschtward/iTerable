let submitnewprod = document.getElementById("mandar-produto-novo");
submitnewprod.addEventListener('submit', function(event) {
	event.preventDefault();
	let nameInput = document.getElementById("name");
	if (nameInput == '') {
		documento.getElementById(mensagem - erro - nome).innerHTML = "Nome não pode ser vazio";
		console.log("errorrrr");
	}
})
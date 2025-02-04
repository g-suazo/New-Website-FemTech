// Carga y renderiza un componente desde un archivo externo
function loadComponent(selector, file) {
  fetch(file)
      .then(response => {
          if (!response.ok) {
              throw new Error(`Error al cargar ${file}: ${response.statusText}`);
          }
          return response.text();
      })
      .then(data => {
          document.querySelector(selector).innerHTML = data;
      })
      .catch(error => console.error(error));
}

// Ejemplo de uso
document.addEventListener("DOMContentLoaded", () => {
  loadComponent("navbar", "../components/navbar.html");
  loadComponent("header", "../components/header.html");
  loadComponent("footer", "../components/footer.html");
});

document.addEventListener("DOMContentLoaded", () => {
    // Seleccionamos todos los párrafos y las imágenes
    const paragraphs = document.querySelectorAll(".line-vertical");
    const images = document.querySelectorAll(".col-lg-5 img");

    // Ocultamos todas las imágenes excepto la primera
    images.forEach((image, index) => {
        if (index !== 0) {
            image.classList.add("hidden");
        }
    });

    // Iteramos sobre los párrafos
    paragraphs.forEach((paragraph, index) => {
        paragraph.addEventListener("click", () => {
            // Ocultamos todas las imágenes
            images.forEach(image => image.classList.add("hidden"));

            // Mostramos la imagen correspondiente
            if (images[index]) {
                images[index].classList.remove("hidden");

                // Desplazamos hacia la imagen mostrada
                images[index].scrollIntoView({ behavior: "smooth", block: "center" });
            }
        });
    });
});


document.addEventListener("DOMContentLoaded", () => {
    const images = document.querySelectorAll('.fade-in-image');
    
    const observer = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
                observer.unobserve(entry.target); // Deja de observar una vez visible
            }
        });
    }, { threshold: 0.1 }); // 10% de la imagen visible

    images.forEach(image => observer.observe(image));
});


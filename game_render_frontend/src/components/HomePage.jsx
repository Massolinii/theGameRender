import React, { useEffect, useState } from 'react';

function HomePage() {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    // Funzione asincrona per ottenere le categorie
    const fetchCategories = async () => {
        const url = "http://localhost:8080/categories"
      try {
        const response = await fetch(url, {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
            },
        });
        if (response.ok) {
          const data = await response.json();
          setCategories(data);
        } else {
          console.error('Errore nella richiesta delle categorie:', response.status);
        }
      } catch (error) {
        console.error('Errore nella richiesta delle categorie:', error);
      }
    };

    fetchCategories();
  }, []);

  return (
    <div>
      <h1>Homepage</h1>
      <h2>Categorie:</h2>
      <ul>
        {categories.map((category) => (
          <li key={category.categoryID}>{category.categoryName}</li>
        ))}
      </ul>
    </div>
  );
}

export default HomePage;
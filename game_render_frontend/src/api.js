// GET

export async function fetchCategory(id) {
  const response = await fetch(`http://localhost:8080/categories/${id}`);
  return response.json();
}

export async function fetchCategories() {
  const response = await fetch("http://localhost:8080/categories");
  return response.json();
}

export async function fetchCollection(id) {
  const response = await fetch(`http://localhost:8080/collections/${id}`);
  return response.json();
}

export async function fetchCollections() {
  const response = await fetch("http://localhost:8080/collections");
  return response.json();
}

export async function fetchCollectionsFromCategory(id) {
  const response = await fetch(
    `http://localhost:8080/collections/categories/${id}`
  );
  return response.json();
}

export async function fetchImagesFromCategory(id) {
  const response = await fetch(`http://localhost:8080/images/category/${id}`);
  return response.json();
}

export async function fetchImagesFromCollection(id) {
  const response = await fetch(`http://localhost:8080/images/collection/${id}`);
  return response.json();
}

export async function fetchUserFavorites(username) {
  const response = await fetch(
    `http://localhost:8080/users/favorites/${username}`
  );
  return response.json();
}

// POST
export async function uploadImage(formData) {
  const response = await fetch("http://localhost:8080/images", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
    body: formData,
  });
  return response;
}

export async function createCollection(collectionData) {
  const response = await fetch("http://localhost:8080/collections", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
    body: JSON.stringify(collectionData),
  });
  return response;
}

// PUT

export const toggleFavorite = async (username, imageId) => {
  const response = await fetch(
    `http://localhost:8080/users/${username}/toggleFavorite/${imageId}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
    }
  );

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  return await response.json();
};

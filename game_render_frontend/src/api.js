// GET

export async function fetchCategory(id) {
  const response = await fetch(`http://localhost:8080/categories/${id}`);
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

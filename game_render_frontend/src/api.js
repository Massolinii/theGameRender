export async function fetchCategory(id) {
  const response = await fetch(`http://localhost:8080/categories/${id}`);
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

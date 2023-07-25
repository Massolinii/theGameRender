const URL = "http://localhost:8080";

// GET

export async function fetchCategory(id) {
  try {
    const response = await fetch(`${URL}/categories/${id}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch category:", error);
    throw error;
  }
}

export async function fetchCategories() {
  try {
    const response = await fetch(`${URL}/categories`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch categories:", error);
    throw error;
  }
}

export async function fetchCollection(id) {
  try {
    const response = await fetch(`${URL}/collections/${id}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch collection:", error);
    throw error;
  }
}

export async function fetchCollections() {
  try {
    const response = await fetch(`${URL}/collections`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch collections:", error);
    throw error;
  }
}

export async function fetchCollectionsFromCategory(id) {
  try {
    const response = await fetch(`${URL}/collections/categories/${id}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch collections from category:", error);
    throw error;
  }
}

export async function fetchImagesFromCategory(id) {
  try {
    const response = await fetch(`${URL}/images/category/${id}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch images from category:", error);
    throw error;
  }
}

export async function fetchImagesFromCollection(id) {
  try {
    const response = await fetch(`${URL}/images/collection/${id}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch images from collection:", error);
    throw error;
  }
}

export async function fetchImagesByKeyword(keyword) {
  try {
    const response = await fetch(`${URL}/images/search/${keyword}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch images by keyword:", error);
    throw error;
  }
}

export async function fetchUserFavorites(username) {
  try {
    const response = await fetch(`${URL}/users/favorites/${username}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch user favorites:", error);
    throw error;
  }
}

// POST
export async function uploadImage(formData) {
  try {
    const response = await fetch(`${URL}/images`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      body: formData,
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response;
  } catch (error) {
    console.error("Failed to upload image:", error);
    throw error;
  }
}

export async function createCollection(collectionData) {
  try {
    const response = await fetch(`${URL}/collections`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      body: JSON.stringify(collectionData),
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error("Failed to create collection:", error);
    throw error;
  }
}

// PUT
export async function toggleFavorite(username, imageId) {
  try {
    const response = await fetch(
      `${URL}/users/${username}/toggleFavorite/${imageId}`,
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
  } catch (error) {
    console.error("Failed to toggle favorite:", error);
    throw error;
  }
}

export async function updateCollection(collectionId, collectionData) {
  try {
    const response = await fetch(`${URL}/collections/${collectionId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      body: JSON.stringify(collectionData),
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error("Failed to update collection:", error);
    throw error;
  }
}

export async function updateImage(imageId, imageData) {
  try {
    const response = await fetch(`${URL}/images/${imageId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      body: JSON.stringify(imageData),
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response;
  } catch (error) {
    console.error("Failed to update image:", error);
    throw error;
  }
}

// DELETE
export async function deleteCollection(collectionId) {
  try {
    const response = await fetch(`${URL}/collections/${collectionId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response;
  } catch (error) {
    console.error("Failed to delete collection:", error);
    throw error;
  }
}
export async function deleteImage(imageId) {
  try {
    const response = await fetch(`${URL}/images/${imageId}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response;
  } catch (error) {
    console.error("Failed to delete image:", error);
    throw error;
  }
}

import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";

const ImageUploadModal = ({ isOpen, onClose }) => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [error, setError] = useState("");
  const [promptText, setPromptText] = useState("");
  const [tags, setTags] = useState("");
  const [collections, setCollections] = useState([]);
  const [selectedCollection, setSelectedCollection] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/collections", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        if (!Array.isArray(data)) {
          console.error("Data is not an array:", data);
          return;
        }
        setCollections(data);
      })
      .catch((error) => console.error("Fetch error:", error));
  }, []);

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handleUpload = async (e) => {
    e.preventDefault();

    if (!selectedFile || !promptText || !selectedCollection) {
      setError("All fields are required");
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);
    formData.append("promptText", promptText);
    formData.append("tags", tags);
    formData.append("collection", selectedCollection);

    const response = await fetch("http://localhost:8080/images", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      body: formData,
    });

    if (response.ok) {
      onClose();
      return "";
    } else {
      const data = await response.json();
      setError(data.message || "An error occurred during file upload");
    }
  };

  return (
    <div>
      {isOpen && (
        <div>
          <h2>Upload Image</h2>
          <form onSubmit={handleUpload}>
            <input type="file" accept="image/*" onChange={handleFileChange} />
            <input
              type="text"
              placeholder="Prompt Text"
              value={promptText}
              onChange={(e) => setPromptText(e.target.value)}
            />
            <input
              type="text"
              placeholder="Tags (separate by ';')"
              value={tags}
              onChange={(e) => setTags(e.target.value)}
            />
            <select
              value={selectedCollection}
              onChange={(e) => setSelectedCollection(e.target.value)}
            >
              {collections.map((collection, index) => (
                <option key={collection.id} value={collection.id}>
                  {collection.category.categoryName +
                    " - " +
                    collection.collectionName}
                </option>
              ))}
            </select>
            {error && <p>{error}</p>}
            <button type="submit">Upload</button>
            <button onClick={onClose}>Close</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default ImageUploadModal;

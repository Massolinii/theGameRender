import { readAndCompressImage } from "browser-image-resizer";
import React, { useEffect, useState } from "react";
import { Alert, Button, Form, Modal } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import { fetchCollections, uploadImage } from "../api.js";

const ImageUploadModal = ({ isOpen, onClose, onUploadSuccess }) => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [error, setError] = useState("");
  const [promptText, setPromptText] = useState("");
  const [tags, setTags] = useState("");
  const [collections, setCollections] = useState([]);
  const [selectedCollection, setSelectedCollection] = useState("");

  useEffect(() => {
    if (localStorage.getItem("token")) {
      // Check if user is authenticated
      fetchCollections()
        .then((data) => {
          if (!Array.isArray(data)) {
            console.error("Data is not an array:", data);
            return;
          }
          setCollections(data);
        })
        .catch((error) => console.error("Fetch error:", error));
    }
  }, []);

  const handleFileChange = async (e) => {
    const file = e.target.files[0];
    const config = {
      quality: 0.8,
      maxWidth: 800,
      maxHeight: 800,
      debug: true,
    };
    try {
      const compressedFile = await readAndCompressImage(file, config);
      setSelectedFile(compressedFile);
    } catch (err) {
      console.error(err);
    }
  };

  const handleUpload = async (e) => {
    e.preventDefault();

    if (!selectedFile || !promptText || !selectedCollection) {
      setError("Image, prompt and collection are required.");
      return;
    }

    const formData = new FormData();
    formData.append("image", selectedFile);
    formData.append("promptText", promptText);
    formData.append("tags", tags);
    formData.append("collectionId", selectedCollection);

    const response = await uploadImage(formData);

    if (response.ok) {
      onClose();
      onUploadSuccess("The image was upload successfully");
    } else {
      const data = await response.json();
      setError(data.message || "An error occurred during file upload");
    }
  };

  return (
    <Modal show={isOpen} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Upload Image </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form className="" onSubmit={handleUpload}>
          <Form.Group>
            <Form.Label>Image</Form.Label>
            <Form.Control
              type="file"
              accept="image/*"
              onChange={handleFileChange}
            />
          </Form.Group>
          <hr />
          <Form.Group>
            <Form.Label>
              Prompt Text <span className="text-muted">(Max lenght 512)</span>
            </Form.Label>
            <Form.Control
              type="text"
              placeholder="Prompt Text"
              value={promptText}
              onChange={(e) => setPromptText(e.target.value)}
            />
          </Form.Group>
          <hr />
          <Form.Group>
            <Form.Label>
              Tags <span className="text-muted">(separated by " ; ")</span>
            </Form.Label>
            <Form.Control
              type="text"
              placeholder="Tags"
              value={tags}
              onChange={(e) => setTags(e.target.value)}
            />
          </Form.Group>
          <hr />
          <Form.Group>
            <Form.Label>Collection</Form.Label>
            <Form.Control
              as="select"
              value={selectedCollection}
              onChange={(e) => setSelectedCollection(e.target.value)}
            >
              {collections.map((collection, index) => (
                <option
                  key={collection.collectionID}
                  value={collection.collectionID}
                >
                  {collection.category.categoryName +
                    " - " +
                    collection.collectionName}
                </option>
              ))}
            </Form.Control>
          </Form.Group>
          <hr />

          {error && <Alert variant="danger">{error}</Alert>}
          <Button type="submit">Upload</Button>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ImageUploadModal;

import React, { useState, useEffect } from "react";
import { Button, Modal, Form, Alert } from "react-bootstrap";
import { createCollection, fetchCategories } from "../api.js";

const CollectionCreateModal = ({ isOpen, onClose, onCreateSuccess }) => {
  const [collectionName, setCollectionName] = useState("");
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    fetchCategories().then(setCategories).catch(console.error);
  }, []);

  const handleCreate = async (e) => {
    e.preventDefault();

    if (!collectionName || !selectedCategory) {
      setError("Collection Name and Category are required.");
      return;
    }

    const collectionData = {
      collectionName: collectionName,
      category: {
        categoryID: selectedCategory,
      },
    };

    try {
      const createdCollection = await createCollection(collectionData);
      onClose();
      onCreateSuccess(
        `The collection "${createdCollection.collectionName}" was created successfully`
      );
    } catch (error) {
      setError(error.message || "An error occurred during creation");
    }
  };

  return (
    <Modal show={isOpen} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Create Collection</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form className="" onSubmit={handleCreate}>
          <Form.Group>
            <Form.Label>Collection Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Collection Name"
              value={collectionName}
              onChange={(e) => setCollectionName(e.target.value)}
            />
          </Form.Group>
          <hr />
          <Form.Group>
            <Form.Label>Category</Form.Label>
            <Form.Control
              as="select"
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              {categories.map((category, index) => (
                <option key={category.categoryID} value={category.categoryID}>
                  {category.categoryName}
                </option>
              ))}
            </Form.Control>
          </Form.Group>
          <hr />
          {error && <Alert variant="danger">{error}</Alert>}
          <Button type="submit">Create</Button>
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

export default CollectionCreateModal;

import React, { useState, useEffect, useContext } from "react";
import { fetchRequests, deleteRequest } from "../api";
import { AuthContext } from "../AuthContext";

const AdminDashboard = () => {
  const [requests, setRequests] = useState([]);
  const { user } = useContext(AuthContext);

  const fetchAllRequests = async () => {
    try {
      const data = await fetchRequests();
      setRequests(data);
    } catch (error) {
      console.error("Error fetching requests:", error);
    }
  };

  useEffect(() => {
    fetchAllRequests();
  }, []);

  const handleDeleteRequest = async (id) => {
    try {
      await deleteRequest(id);
      fetchAllRequests();
    } catch (error) {
      console.error("Error deleting request:", error);
    }
  };

  const isAdmin = () => {
    return user && user.roles && user.roles.includes("ROLE_ADMIN");
  };

  return (
    <div className="category-page-container text-white py-5 px-3 full-screen">
      <h2 className="p-3">ADMIN ONLY </h2>
      <h3 className="p-3">Users Requests</h3>
      {requests.length === 0 ? (
        <p>There are no request right now. Good job!</p>
      ) : (
        <ul>
          {requests.map((request, index) => (
            <li key={index}>
              {request.content}{" "}
              {isAdmin() && (
                <button onClick={() => handleDeleteRequest(request.id)}>
                  Delete
                </button>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default AdminDashboard;

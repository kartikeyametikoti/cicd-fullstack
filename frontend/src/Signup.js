import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./styles.css"; // Import the CSS file
  
function Signup() { 
  const [formData, setFormData] = useState({ username: "", email: "", password: "" });
  const navigate = useNavigate(); 

  const handleChange = (e) => { 
    setFormData({ ...formData, [e.target.name]: e.target.value }); 
  };    
        
  const handleSubmit = async (e) => {
    e.preventDefault();
    try { 
      // const response = await axios.post("http://54.221.123.218:5000/signup", formData);
     // const response = await axios.post(`${process.env.REACT_APP_BACKEND_URL}/api/signup`, formData);
      const response = await axios.post(`${process.env.REACT_APP_BACKEND_URL}/signup`, formData);
      // const response = await axios.post("http://backend-loadbalancer-ecs-1656454160.us-east-1.elb.amazonaws.com/signup", formData);
      alert(response.data.message);
      navigate("/login");
    } catch (error) {
      alert(error.response.data.message);
    }
  }; 
   
  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>Signup</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" name="username" placeholder="Username" onChange={handleChange} required />
          <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
          <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
          <button type="submit">Signup</button>
        </form>
        <p>Already have an account?</p>
        <button className="secondary" onClick={() => navigate("/login")}>Login</button>
      </div>
    </div>
  );
}

export default Signup;

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./styles.css"; // Import the CSS file

function Login() {
  const [formData, setFormData] = useState({ email: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // const response = await axios.post("http://3.227.19.133:5000/login", formData);
      const response = await axios.post("http://backend-load-ecs-1073322385.us-east-1.elb.amazonaws.com/login", formData);
      // const response = await axios.post("http://backend-loadbalancer-ecs-1656454160.us-east-1.elb.amazonaws.com/login", formData);
      alert(response.data.message);
    } catch (error) {
      alert(error.response.data.message);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
          <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
          <button type="submit">Login</button>
        </form>
        <p>Don't have an account?</p>
        <button className="secondary" onClick={() => navigate("/")}>Signup</button>
      </div>
    </div>
  );
}

export default Login;

# Rate Limiting with Redis

This project implements **Rate Limiting** using **Redis** to control the rate of API requests based on configurable limits. It ensures fair usage of resources and prevents abuse by enforcing request limits. Additionally, an API is provided to check the current usage count for a specific client.

## Features

- **Configurable Rate Limits**:  
  Define and manage rate limits dynamically based on application requirements.

- **Redis Integration**:  
  Use Redis as a high-performance, in-memory data store to track and enforce rate limits.

- **API for Current Usage**:  
  Expose an API to check the current rate limit count for a specific client or endpoint.

- **Efficient Tracking**:  
  Maintain accurate request counts with low latency using Redis operations.

- **Scalable Solution**:  
  Designed to handle high throughput and concurrent requests efficiently in distributed systems.

- **Prevention of Abuse**:  
  Block clients exceeding their allowed request limit while ensuring legitimate users have seamless access.


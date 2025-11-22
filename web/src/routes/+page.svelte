<script lang="ts">
	// Use environment variable with production-safe fallback
	const apiUrl = import.meta.env.VITE_API_URL || (import.meta.env.PROD ? '/api' : 'http://localhost:8080');
	let apiStatus = 'Checking...';
	
	async function checkApi() {
		try {
			const response = await fetch(`${apiUrl}/health`);
			const data = await response.json();
			apiStatus = data.status === 'healthy' ? '✅ API Online' : '❌ API Offline';
		} catch (error) {
			apiStatus = '❌ API Offline';
		}
	}
	
	checkApi();
</script>

<main>
	<div class="container">
		<h1>🎮 ChordKnight</h1>
		<p class="tagline">Chess utilities, server tools, and a modern music system behind a smooth, zero-command UI.</p>
		
		<div class="status">
			<h2>System Status</h2>
			<p>API: {apiStatus}</p>
		</div>
		
		<div class="features">
			<h2>Features</h2>
			<div class="feature-grid">
				<div class="feature">
					<h3>♟️ Chess</h3>
					<p>Play chess games with move validation and state management</p>
				</div>
				<div class="feature">
					<h3>🎵 Music</h3>
					<p>Stream music with Lavalink integration</p>
				</div>
				<div class="feature">
					<h3>🤖 Bot</h3>
					<p>Discord bot with dynamic feature loading</p>
				</div>
				<div class="feature">
					<h3>🌐 API</h3>
					<p>RESTful API built with Ktor</p>
				</div>
			</div>
		</div>
	</div>
</main>

<style>
	:global(body) {
		margin: 0;
		padding: 0;
		font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
		background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
		color: white;
		min-height: 100vh;
	}
	
	main {
		display: flex;
		justify-content: center;
		align-items: center;
		min-height: 100vh;
		padding: 2rem;
	}
	
	.container {
		max-width: 800px;
		width: 100%;
	}
	
	h1 {
		font-size: 3rem;
		margin-bottom: 0.5rem;
		text-align: center;
	}
	
	.tagline {
		text-align: center;
		font-size: 1.2rem;
		opacity: 0.9;
		margin-bottom: 3rem;
	}
	
	.status {
		background: rgba(255, 255, 255, 0.1);
		padding: 1.5rem;
		border-radius: 10px;
		margin-bottom: 2rem;
		backdrop-filter: blur(10px);
	}
	
	.status h2 {
		margin-top: 0;
	}
	
	.features {
		background: rgba(255, 255, 255, 0.1);
		padding: 1.5rem;
		border-radius: 10px;
		backdrop-filter: blur(10px);
	}
	
	.features h2 {
		margin-top: 0;
	}
	
	.feature-grid {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
		gap: 1rem;
		margin-top: 1rem;
	}
	
	.feature {
		background: rgba(255, 255, 255, 0.1);
		padding: 1rem;
		border-radius: 8px;
	}
	
	.feature h3 {
		margin-top: 0;
		margin-bottom: 0.5rem;
	}
	
	.feature p {
		margin: 0;
		opacity: 0.9;
		font-size: 0.9rem;
	}
</style>

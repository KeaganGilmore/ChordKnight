<script lang="ts">
	import '../app.css';
	import { page } from '$app/stores';

	const navItems = [
		{ path: '/', label: 'Dashboard', icon: '🏠' },
		{ path: '/music', label: 'Music', icon: '🎵' },
		{ path: '/chess', label: 'Chess', icon: '♟️' },
		{ path: '/settings', label: 'Settings', icon: '⚙️' }
	];
</script>

<div class="min-h-screen flex bg-gradient-to-br from-cyber-dark via-gray-900 to-cyber-dark">
	<!-- Sidebar -->
	<aside class="w-64 bg-black bg-opacity-50 backdrop-blur-sm border-r border-cyber-blue border-opacity-30">
		<div class="p-6">
			<h1 class="text-2xl font-bold bg-gradient-to-r from-cyber-blue to-cyber-purple bg-clip-text text-transparent">
				ChordKnight
			</h1>
			<p class="text-xs text-gray-400 mt-1">Chess • Music • Discord</p>
		</div>

		<nav class="px-4 space-y-2">
			{#each navItems as item}
				<a
					href={item.path}
					class="flex items-center gap-3 px-4 py-3 rounded-lg transition-all duration-200
						{$page.url.pathname === item.path 
							? 'bg-gradient-to-r from-cyber-blue to-cyber-purple text-white shadow-lg shadow-cyber-blue/50' 
							: 'text-gray-400 hover:text-white hover:bg-white hover:bg-opacity-5'}"
				>
					<span class="text-xl">{item.icon}</span>
					<span class="font-medium">{item.label}</span>
				</a>
			{/each}
		</nav>

		<div class="absolute bottom-0 left-0 right-0 p-4 bg-gradient-to-t from-black to-transparent">
			<div class="bg-black bg-opacity-50 rounded-lg p-4 border border-cyber-purple border-opacity-30">
				<p class="text-xs text-gray-400">Server Status</p>
				<div class="flex items-center gap-2 mt-2">
					<div class="w-2 h-2 bg-green-500 rounded-full animate-pulse"></div>
					<span class="text-sm text-gray-300">Online</span>
				</div>
			</div>
		</div>
	</aside>

	<!-- Main Content -->
	<main class="flex-1 p-8 overflow-y-auto">
		<slot />
	</main>
</div>

<style>
	:global(body) {
		margin: 0;
		overflow: hidden;
	}
</style>

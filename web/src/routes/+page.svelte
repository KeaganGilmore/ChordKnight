<script lang="ts">
	import { onMount } from 'svelte';

	let guilds: any[] = [];
	let loading = true;

	onMount(async () => {
		try {
			const response = await fetch('/api/guilds');
			guilds = await response.json();
		} catch (error) {
			console.error('Failed to fetch guilds:', error);
		} finally {
			loading = false;
		}
	});
</script>

<div class="max-w-7xl mx-auto">
	<div class="mb-8">
		<h1 class="text-4xl font-bold bg-gradient-to-r from-cyber-blue via-cyber-purple to-cyber-pink bg-clip-text text-transparent">
			Welcome to ChordKnight
		</h1>
		<p class="text-gray-400 mt-2">Manage your Discord bot's music and chess features</p>
	</div>

	<!-- Stats Cards -->
	<div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
		<div class="bg-gradient-to-br from-cyber-blue/10 to-cyber-blue/5 rounded-xl p-6 border border-cyber-blue/30">
			<div class="flex items-center justify-between">
				<div>
					<p class="text-gray-400 text-sm">Active Servers</p>
					<p class="text-3xl font-bold text-cyber-blue mt-1">{guilds.length}</p>
				</div>
				<div class="text-4xl">🏰</div>
			</div>
		</div>

		<div class="bg-gradient-to-br from-cyber-purple/10 to-cyber-purple/5 rounded-xl p-6 border border-cyber-purple/30">
			<div class="flex items-center justify-between">
				<div>
					<p class="text-gray-400 text-sm">Songs Played</p>
					<p class="text-3xl font-bold text-cyber-purple mt-1">0</p>
				</div>
				<div class="text-4xl">🎵</div>
			</div>
		</div>

		<div class="bg-gradient-to-br from-cyber-pink/10 to-cyber-pink/5 rounded-xl p-6 border border-cyber-pink/30">
			<div class="flex items-center justify-between">
				<div>
					<p class="text-gray-400 text-sm">Chess Games</p>
					<p class="text-3xl font-bold text-cyber-pink mt-1">0</p>
				</div>
				<div class="text-4xl">♟️</div>
			</div>
		</div>
	</div>

	<!-- Guilds List -->
	<div class="bg-black bg-opacity-50 rounded-xl border border-gray-800">
		<div class="p-6 border-b border-gray-800">
			<h2 class="text-2xl font-bold">Your Servers</h2>
		</div>

		<div class="p-6">
			{#if loading}
				<div class="text-center py-12">
					<div class="inline-block w-8 h-8 border-4 border-cyber-blue border-t-transparent rounded-full animate-spin"></div>
					<p class="text-gray-400 mt-4">Loading servers...</p>
				</div>
			{:else if guilds.length === 0}
				<div class="text-center py-12">
					<p class="text-gray-400">No servers found</p>
					<p class="text-sm text-gray-500 mt-2">Add the bot to a server to get started</p>
				</div>
			{:else}
				<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
					{#each guilds as guild}
						<a
							href={`/guild/${guild.id}`}
							class="bg-gradient-to-br from-gray-900 to-gray-800 rounded-lg p-4 border border-gray-700 hover:border-cyber-blue transition-all duration-200 hover:shadow-lg hover:shadow-cyber-blue/20"
						>
							<div class="flex items-center gap-4">
								{#if guild.iconUrl}
									<img src={guild.iconUrl} alt={guild.name} class="w-12 h-12 rounded-full" />
								{:else}
									<div class="w-12 h-12 rounded-full bg-gradient-to-br from-cyber-blue to-cyber-purple flex items-center justify-center text-xl font-bold">
										{guild.name.charAt(0)}
									</div>
								{/if}
								<div>
									<h3 class="font-semibold">{guild.name}</h3>
									<p class="text-sm text-gray-400">ID: {guild.id}</p>
								</div>
							</div>
						</a>
					{/each}
				</div>
			{/if}
		</div>
	</div>
</div>
